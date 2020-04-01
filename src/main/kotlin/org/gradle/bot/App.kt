/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.gradle.bot

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.reflect.ClassPath
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.name.Names
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.core.net.ProxyOptions
import io.vertx.core.spi.VerticleFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.http.httpServerOptionsOf
import java.lang.reflect.Modifier
import java.util.concurrent.Callable
import javax.inject.Inject
import org.gradle.bot.client.GitHubClient
import org.gradle.bot.eventhandlers.WebHookEventHandler
import org.gradle.bot.security.GithubSignatureChecker
import org.gradle.bot.security.LenientGitHubSignatureCheck
import org.gradle.bot.security.Sha1GitHubSignatureChecker
import org.gradle.bot.webhookhandlers.GitHubWebHookHandler
import org.gradle.bot.webhookhandlers.SlackWebHookHandler
import org.gradle.bot.webhookhandlers.TeamCityWebHookHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val objectMapper: ObjectMapper = ObjectMapper()
val logger: Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)

fun start(verticleClass: Class<out Verticle>, guiceModuleClass: Class<out Module>): Future<Injector> {
    val vertx = Vertx.vertx()
    val injector = Guice.createInjector(guiceModuleClass.getConstructor(Vertx::class.java).newInstance(vertx))
    vertx.exceptionHandler { logger.error("", it) }
    vertx.registerVerticleFactory(GuiceVerticleFactory(injector, verticleClass))
    return vertx.deployVerticle(verticleClass.name).map(injector)
}

fun main() {
    start(GradleBotVerticle::class.java, GradleBotAppModule::class.java)
}

class GuiceVerticleFactory(private val injector: Injector, private val verticleClass: Class<out Verticle>) : VerticleFactory {
    override fun createVerticle(verticleName: String?, classLoader: ClassLoader?, promise: Promise<Callable<Verticle>>?) {
        try {
            promise!!.complete(Callable {
                try {
                    injector.getInstance(verticleClass)
                } catch (e: Throwable) {
                    logger.error("", e)
                    throw e
                }
            })
        } catch (e: Throwable) {
            logger.error("", e)
            promise!!.fail(e)
        }
    }

    override fun prefix(): String = GradleBotVerticle::class.java.simpleName
}

open class GradleBotAppModule(private val vertx: Vertx) : AbstractModule() {
    private val client = WebClient.create(vertx,
        WebClientOptions().also { options ->
            if (System.getProperty("http.proxyHost") != null && System.getProperty("http.proxyPort") != null) {
                options.proxyOptions = ProxyOptions().also {
                    it.host = System.getProperty("http.proxyHost")
                    it.port = System.getProperty("http.proxyPort").toInt()
                }
            }
        })

    override fun configure() {
        bind(Vertx::class.java).toInstance(vertx)
        bind(WebClient::class.java).toInstance(client)
        bindAccessTokens()
        bindGitHubSignatureCheckerOnSecret()
    }

    open fun bindAccessTokens() {
        listOf("GITHUB_ACCESS_TOKEN", "TEAMCITY_ACCESS_TOKEN").forEach(this::bindEnv)
    }

    open fun bindGitHubSignatureCheckerOnSecret() {
        when (System.getenv("GITHUB_WEBHOOK_SECRET")) {
            null -> bind(GithubSignatureChecker::class.java).toInstance(LenientGitHubSignatureCheck.INSTANCE)
            else -> {
                bindEnv("GITHUB_WEBHOOK_SECRET")
                bind(GithubSignatureChecker::class.java).to(Sha1GitHubSignatureChecker::class.java)
            }
        }
    }

    private fun bindEnv(envName: String) {
        val envValue = System.getenv(envName) ?: throw IllegalStateException("Env $envName must be set!")
        bind(String::class.java).annotatedWith(Names.named(envName)).toInstance(envValue)
    }
}

class GradleBotVerticle @Inject constructor(
    private val injector: Injector,
    private val gitHubWebHookHandler: GitHubWebHookHandler,
    private val teamCityWebHookHandler: TeamCityWebHookHandler,
    private val slackWebHookEventHandler: SlackWebHookHandler,
    private val gitHubClient: GitHubClient
) : AbstractVerticle() {
    private val logger: Logger = LoggerFactory.getLogger(GradleBotVerticle::class.java.name)
    private val port by lazy {
        System.getenv("HTTP_PORT")?.toInt() ?: 8080
    }

    @Suppress("UNCHECKED_CAST")
    private
    fun registerEventHandlers(injector: Injector) {
        try {
            val packageName = GradleBotVerticle::class.java.`package`.name
            ClassPath.from(GradleBotVerticle::class.java.classLoader).getTopLevelClassesRecursive(packageName).forEach {
                val klass = it.load()
                if (WebHookEventHandler::class.java.isAssignableFrom(klass) && !Modifier.isAbstract(klass.modifiers)) {
                    val eventHandler: WebHookEventHandler = injector.getInstance(klass) as WebHookEventHandler
                    vertx.eventBus().consumer<String>(eventHandler.eventAddress, eventHandler)
                }
            }
        } catch (e: Throwable) {
            logger.error("", e)
            throw e
        }
    }

    override fun start(startFuture: Promise<Void>) {
        registerEventHandlers(injector)
        gitHubClient.init().onSuccess {
            logger.info("GitHub client initialized, I am {}", gitHubClient.whoAmI())

            val router = createRouter()

            val serverOptions = httpServerOptionsOf(port = port, ssl = false, compressionSupported = true)
            vertx.createHttpServer(serverOptions)
                .requestHandler(router)
                .listen { result ->
                    if (result.succeeded()) {
                        logger.info("App started.")
                        startFuture.complete()
                    } else {
                        logger.error("App failed to start.", result.cause())
                        startFuture.fail(result.cause())
                    }
                }
        }
    }

    private fun createRouter() = Router.router(vertx).apply {
        route("/*").handler(BodyHandler.create())
        post("/github").handler(gitHubWebHookHandler)
        post("/teamcity").handler(teamCityWebHookHandler)
        post("/slack").handler(slackWebHookEventHandler)
        errorHandler(500) { it?.failure()?.printStackTrace() }
    }

    /**
     * Extension to the HTTP response to output JSON objects.
     */
}

fun HttpServerResponse.endWithJson(obj: Any) {
    this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
}

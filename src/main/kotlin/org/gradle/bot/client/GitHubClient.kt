package org.gradle.bot.client

import com.fasterxml.jackson.annotation.JsonProperty
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.net.ProxyOptions
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import org.gradle.bot.model.CommitStatus
import org.gradle.bot.model.PullRequestWithComments
import org.gradle.bot.model.WhoAmI
import org.gradle.bot.model.addCommentMutation
import org.gradle.bot.model.pullRequestsWithCommentsQuery
import org.gradle.bot.model.whoAmIQuery
import org.gradle.bot.objectMapper
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubClient @Inject constructor(vertx: Vertx) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = WebClient.create(vertx,
            WebClientOptions().also { options ->
                if (System.getProperty("http.proxyHost") != null && System.getProperty("http.proxyPort") != null) {
                    options.proxyOptions = ProxyOptions().also {
                        it.host = System.getProperty("http.proxyHost")
                        it.port = System.getProperty("http.proxyPort").toInt()
                    }
                }
            })
    private val authHeader by lazy {
        val githubToken = System.getenv("GITHUB_ACCESS_TOKEN") ?: throw IllegalStateException("Must set env variable GITHUB_ACCESS_TOKEN")
        "bearer $githubToken"
    }

    private var myself: String? = null

    fun init(): Future<String> {
        return doQueryOrMutation(WhoAmI::class.java, whoAmIQuery).map {
            it.data.viewer.login
        }.onSuccess {
            myself = it
        }.onFailure {
            logger.error("", it)
        }
    }

    fun whoAmI(): String {
        return myself ?: throw IllegalStateException("Not initialized!")
    }

    fun comment(subjectId: String, commentBody: String): Future<*> {
        return doQueryOrMutation(Map::class.java, addCommentMutation(subjectId, commentBody))
    }

    fun getPullRequestWithComments(repo: String, number: Int): Future<PullRequestWithComments> =
            repo.split('/').let {
                pullRequestsWithCommentsQuery(it[0], it[1], number)
            }.let {
                doQueryOrMutation(PullRequestWithComments::class.java, it)
            }

    private
    fun <T> doQueryOrMutation(klass: Class<T>, queryString: String): Future<T> {
        return client.postAbs("https://api.github.com/graphql")
                .putHeader("Accept", "application/json")
                .putHeader("Content-Type", "application/json")
                .putHeader("Authorization", authHeader)
                .sendBuffer(Buffer.buffer(toQueryJson(queryString)))
                .map {
                    val response = it.bodyAsString()
                    logger.debug("Get response while querying {}\n{}\n{}", klass.simpleName, queryString, response)
                    objectMapper.readValue(response, klass)
                }.onFailure {
                    logger.error("Error while querying ${klass.simpleName}\n$queryString", it)
                }
    }

    private
    fun toQueryJson(query: String) = objectMapper.writeValueAsString(mapOf("query" to query))

    fun createCommitStatus(repoName: String, sha: String, dependencies: List<String>, status: CommitStatus) {
        // https://developer.github.com/v3/repos/statuses/
        val url = repoName.split('/').let { "https://api.github.com/repos/${it[0]}/${it[1]}/statuses/$sha" }
        dependencies.forEach {
            client.postAbs(url)
                    .putHeader("Accept", "application/json")
                    .putHeader("Content-Type", "application/json")
                    .putHeader("Authorization", authHeader)
                    .sendBuffer(
                            Buffer.buffer(
                                    objectMapper.writeValueAsString(
                                            CommitStatusObject(it,
                                                    "TeamCity build finished",
                                                    status.name.toLowerCase(),
                                                    "https://builds.gradle.org"))
                            )
                    ).onFailure {
                        logger.error("Error when creating commit status {} to {}", status, url)
                    }.onSuccess {
                        logger.info("Get response: {}", it.bodyAsString())
                        logger.info("Successfully created commit status {} to {}", status, url)
                    }
        }
    }
}

/*
{
  "state": "success",
  "target_url": "https://example.com/build/status",
  "description": "The build succeeded!",
  "context": "continuous-integration/jenkins"
}
 */

data class CommitStatusObject(
        @JsonProperty("context")
        var context: String,
        @JsonProperty("description")
        var description: String,
        @JsonProperty("state")
        var state: String,
        @get:JsonProperty("target_url")
        var targetUrl: String
)

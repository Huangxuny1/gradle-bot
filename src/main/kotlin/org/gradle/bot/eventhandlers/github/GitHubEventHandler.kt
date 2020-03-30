package org.gradle.bot.eventhandlers.github

import io.vertx.core.Handler
import io.vertx.core.eventbus.Message
import org.gradle.bot.client.GitHubClient
import org.gradle.bot.client.TeamCityClient
import org.gradle.bot.eventhandlers.github.issuecomment.PullRequestContext
import org.gradle.bot.model.GitHubEvent
import org.gradle.bot.model.IssueCommentGitHubEvent
import org.gradle.bot.objectMapper
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import javax.inject.Singleton

interface GitHubEventHandler : Handler<Message<String>> {
    val eventType: String
}

@Singleton
class IssueCommentEventHandler @Inject constructor(private val gitHubClient: GitHubClient,
                                                   private val teamCityClient: TeamCityClient
) : AbstractGitHubEventHandler<IssueCommentGitHubEvent>() {
    override fun handleEvent(event: IssueCommentGitHubEvent) {
        gitHubClient.getPullRequestWithComments(event.repository.fullName, event.issue.number).onSuccess {
            PullRequestContext(gitHubClient, teamCityClient, it).processCommand(event.comment.id)
        }
    }
}

@Suppress("UNCHECKED_CAST")
abstract class AbstractGitHubEventHandler<T : GitHubEvent> : GitHubEventHandler {
    private val eventClass by lazy {
        val superClass = javaClass.genericSuperclass
        (superClass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }
    override val eventType: String by lazy {
        GitHubEvent.EVENT_TYPES.inverse().getValue(eventClass)
    }

    override fun handle(event: Message<String>?) {
        handleEvent(objectMapper.readValue(event?.body(), eventClass))
    }

    abstract fun handleEvent(event: T)
}
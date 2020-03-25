package org.gradle.bot.model

import com.fasterxml.jackson.annotation.JsonProperty


/*
API doc:

https://developer.github.com/v3/repos/statuses/

Header:

Request URL: http://webhook.yinghekongjian.com:5000/events
Request method: POST
content-type: application/json
Expect:
User-Agent: GitHub-Hookshot/6c4e595
X-GitHub-Delivery: abaab162-6d78-11ea-8631-e7e36c6b55e8
X-GitHub-Event: status

Payload:

{
  "id": 9169913589,
  "sha": "b5fec4425d3681b5d48290daf2c920f1ab5d22cd",
  "name": "hcsp/save-pull-requests-to-csv",
  "target_url": "https://circleci.com/gh/hcsp/save-pull-requests-to-csv/426?utm_campaign=vcs-integration-link&utm_medium=referral&utm_source=github-build-link",
  "avatar_url": "https://avatars2.githubusercontent.com/oa/4808?v=4",
  "context": "ci/circleci: test",
  "description": "Your tests failed on CircleCI",
  "state": "failure",
  "commit": {
    "sha": "b5fec4425d3681b5d48290daf2c920f1ab5d22cd",
    "node_id": "MDY6Q29tbWl0MjAwMzg2MDM5OmI1ZmVjNDQyNWQzNjgxYjVkNDgyOTBkYWYyYzkyMGYxYWI1ZDIyY2Q=",
    "commit": {
      "author": {
        "name": "Hcsp Bot",
        "email": "robot@jirengu.com",
        "date": "2020-03-24T02:36:59Z"
      },
      "committer": {
        "name": "Hcsp Bot",
        "email": "robot@jirengu.com",
        "date": "2020-03-24T02:36:59Z"
      },
      "message": "Revert \"爬取GitHub的Pull request并存储为CSV文件 (#99)\"\n\nThis reverts commit f2009746fcf196b8432491eee3b8f67245b864f2.",
      "tree": {
        "sha": "32760b05f48d129443756a5805d9f44a2bfd0c9c",
        "url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/trees/32760b05f48d129443756a5805d9f44a2bfd0c9c"
      },
      "url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/commits/b5fec4425d3681b5d48290daf2c920f1ab5d22cd",
      "comment_count": 0,
      "verification": {
        "verified": false,
        "reason": "unsigned",
        "signature": null,
        "payload": null
      }
    },
    "url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/commits/b5fec4425d3681b5d48290daf2c920f1ab5d22cd",
    "html_url": "https://github.com/hcsp/save-pull-requests-to-csv/commit/b5fec4425d3681b5d48290daf2c920f1ab5d22cd",
    "comments_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/commits/b5fec4425d3681b5d48290daf2c920f1ab5d22cd/comments",
    "author": {
      "login": "hcsp-bot",
      "id": 49554844,
      "node_id": "MDQ6VXNlcjQ5NTU0ODQ0",
      "avatar_url": "https://avatars2.githubusercontent.com/u/49554844?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/hcsp-bot",
      "html_url": "https://github.com/hcsp-bot",
      "followers_url": "https://api.github.com/users/hcsp-bot/followers",
      "following_url": "https://api.github.com/users/hcsp-bot/following{/other_user}",
      "gists_url": "https://api.github.com/users/hcsp-bot/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/hcsp-bot/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/hcsp-bot/subscriptions",
      "organizations_url": "https://api.github.com/users/hcsp-bot/orgs",
      "repos_url": "https://api.github.com/users/hcsp-bot/repos",
      "events_url": "https://api.github.com/users/hcsp-bot/events{/privacy}",
      "received_events_url": "https://api.github.com/users/hcsp-bot/received_events",
      "type": "User",
      "site_admin": false
    },
    "committer": {
      "login": "hcsp-bot",
      "id": 49554844,
      "node_id": "MDQ6VXNlcjQ5NTU0ODQ0",
      "avatar_url": "https://avatars2.githubusercontent.com/u/49554844?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/hcsp-bot",
      "html_url": "https://github.com/hcsp-bot",
      "followers_url": "https://api.github.com/users/hcsp-bot/followers",
      "following_url": "https://api.github.com/users/hcsp-bot/following{/other_user}",
      "gists_url": "https://api.github.com/users/hcsp-bot/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/hcsp-bot/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/hcsp-bot/subscriptions",
      "organizations_url": "https://api.github.com/users/hcsp-bot/orgs",
      "repos_url": "https://api.github.com/users/hcsp-bot/repos",
      "events_url": "https://api.github.com/users/hcsp-bot/events{/privacy}",
      "received_events_url": "https://api.github.com/users/hcsp-bot/received_events",
      "type": "User",
      "site_admin": false
    },
    "parents": [
      {
        "sha": "f2009746fcf196b8432491eee3b8f67245b864f2",
        "url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/commits/f2009746fcf196b8432491eee3b8f67245b864f2",
        "html_url": "https://github.com/hcsp/save-pull-requests-to-csv/commit/f2009746fcf196b8432491eee3b8f67245b864f2"
      }
    ]
  },
  "branches": [
    {
      "name": "master",
      "commit": {
        "sha": "b5fec4425d3681b5d48290daf2c920f1ab5d22cd",
        "url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/commits/b5fec4425d3681b5d48290daf2c920f1ab5d22cd"
      },
      "protected": true
    }
  ],
  "created_at": "2020-03-24T02:39:23+00:00",
  "updated_at": "2020-03-24T02:39:23+00:00",
  "repository": {
    "id": 200386039,
    "node_id": "MDEwOlJlcG9zaXRvcnkyMDAzODYwMzk=",
    "name": "save-pull-requests-to-csv",
    "full_name": "hcsp/save-pull-requests-to-csv",
    "private": false,
    "owner": {
      "login": "hcsp",
      "id": 47730780,
      "node_id": "MDEyOk9yZ2FuaXphdGlvbjQ3NzMwNzgw",
      "avatar_url": "https://avatars3.githubusercontent.com/u/47730780?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/hcsp",
      "html_url": "https://github.com/hcsp",
      "followers_url": "https://api.github.com/users/hcsp/followers",
      "following_url": "https://api.github.com/users/hcsp/following{/other_user}",
      "gists_url": "https://api.github.com/users/hcsp/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/hcsp/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/hcsp/subscriptions",
      "organizations_url": "https://api.github.com/users/hcsp/orgs",
      "repos_url": "https://api.github.com/users/hcsp/repos",
      "events_url": "https://api.github.com/users/hcsp/events{/privacy}",
      "received_events_url": "https://api.github.com/users/hcsp/received_events",
      "type": "Organization",
      "site_admin": false
    },
    "html_url": "https://github.com/hcsp/save-pull-requests-to-csv",
    "description": "Java basic practice for beginners: IO",
    "fork": false,
    "url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv",
    "forks_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/forks",
    "keys_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/keys{/key_id}",
    "collaborators_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/collaborators{/collaborator}",
    "teams_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/teams",
    "hooks_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/hooks",
    "issue_events_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/issues/events{/number}",
    "events_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/events",
    "assignees_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/assignees{/user}",
    "branches_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/branches{/branch}",
    "tags_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/tags",
    "blobs_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/blobs{/sha}",
    "git_tags_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/tags{/sha}",
    "git_refs_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/refs{/sha}",
    "trees_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/trees{/sha}",
    "statuses_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/statuses/{sha}",
    "languages_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/languages",
    "stargazers_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/stargazers",
    "contributors_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/contributors",
    "subscribers_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/subscribers",
    "subscription_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/subscription",
    "commits_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/commits{/sha}",
    "git_commits_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/git/commits{/sha}",
    "comments_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/comments{/number}",
    "issue_comment_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/issues/comments{/number}",
    "contents_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/contents/{+path}",
    "compare_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/compare/{base}...{head}",
    "merges_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/merges",
    "archive_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/{archive_format}{/ref}",
    "downloads_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/downloads",
    "issues_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/issues{/number}",
    "pulls_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/pulls{/number}",
    "milestones_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/milestones{/number}",
    "notifications_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/notifications{?since,all,participating}",
    "labels_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/labels{/name}",
    "releases_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/releases{/id}",
    "deployments_url": "https://api.github.com/repos/hcsp/save-pull-requests-to-csv/deployments",
    "created_at": "2019-08-03T14:40:20Z",
    "updated_at": "2020-03-24T02:37:04Z",
    "pushed_at": "2020-03-24T02:37:02Z",
    "git_url": "git://github.com/hcsp/save-pull-requests-to-csv.git",
    "ssh_url": "git@github.com:hcsp/save-pull-requests-to-csv.git",
    "clone_url": "https://github.com/hcsp/save-pull-requests-to-csv.git",
    "svn_url": "https://github.com/hcsp/save-pull-requests-to-csv",
    "homepage": null,
    "size": 217,
    "stargazers_count": 0,
    "watchers_count": 0,
    "language": "Java",
    "has_issues": true,
    "has_projects": true,
    "has_downloads": true,
    "has_wiki": true,
    "has_pages": false,
    "forks_count": 28,
    "mirror_url": null,
    "archived": false,
    "disabled": false,
    "open_issues_count": 3,
    "license": null,
    "forks": 28,
    "open_issues": 3,
    "watchers": 0,
    "default_branch": "master"
  },
  "organization": {
    "login": "hcsp",
    "id": 47730780,
    "node_id": "MDEyOk9yZ2FuaXphdGlvbjQ3NzMwNzgw",
    "url": "https://api.github.com/orgs/hcsp",
    "repos_url": "https://api.github.com/orgs/hcsp/repos",
    "events_url": "https://api.github.com/orgs/hcsp/events",
    "hooks_url": "https://api.github.com/orgs/hcsp/hooks",
    "issues_url": "https://api.github.com/orgs/hcsp/issues",
    "members_url": "https://api.github.com/orgs/hcsp/members{/member}",
    "public_members_url": "https://api.github.com/orgs/hcsp/public_members{/member}",
    "avatar_url": "https://avatars3.githubusercontent.com/u/47730780?v=4",
    "description": "A hardcore space."
  },
  "sender": {
    "login": "blindpirate",
    "id": 12689835,
    "node_id": "MDQ6VXNlcjEyNjg5ODM1",
    "avatar_url": "https://avatars3.githubusercontent.com/u/12689835?v=4",
    "gravatar_id": "",
    "url": "https://api.github.com/users/blindpirate",
    "html_url": "https://github.com/blindpirate",
    "followers_url": "https://api.github.com/users/blindpirate/followers",
    "following_url": "https://api.github.com/users/blindpirate/following{/other_user}",
    "gists_url": "https://api.github.com/users/blindpirate/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/blindpirate/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/blindpirate/subscriptions",
    "organizations_url": "https://api.github.com/users/blindpirate/orgs",
    "repos_url": "https://api.github.com/users/blindpirate/repos",
    "events_url": "https://api.github.com/users/blindpirate/events{/privacy}",
    "received_events_url": "https://api.github.com/users/blindpirate/received_events",
    "type": "User",
    "site_admin": false
  }
}
 */

data class CommitStatusEvent(
        @JsonProperty("avatar_url")
        var avatarUrl: String,
        @JsonProperty("branches")
        var branches: List<Branch>,
        @JsonProperty("commit")
        var commit: Commit,
        @JsonProperty("context")
        var context: String,
        @JsonProperty("created_at")
        var createdAt: String,
        @JsonProperty("description")
        var description: String,
        @JsonProperty("id")
        var id: Long,
        @JsonProperty("name")
        var name: String,
        @JsonProperty("organization")
        override var organization: Organization,
        @JsonProperty("repository")
        override var repository: Repository,
        @JsonProperty("sender")
        override var sender: User,
        @JsonProperty("sha")
        var sha: String,
        @JsonProperty("state")
        var state: String,
        @JsonProperty("target_url")
        var targetUrl: String,
        @JsonProperty("updated_at")
        var updatedAt: String
) : GitHubEvent {
    data class Branch(
            @JsonProperty("commit")
            var commit: Commit,
            @JsonProperty("name")
            var name: String,
            @JsonProperty("protected")
            var `protected`: Boolean
    ) {
        data class Commit(
                @JsonProperty("sha")
                var sha: String,
                @JsonProperty("url")
                var url: String
        )
    }

    data class Commit(
            @JsonProperty("author")
            var author: User,
            @JsonProperty("comments_url")
            var commentsUrl: String,
            @JsonProperty("commit")
            var commit: Commit,
            @JsonProperty("committer")
            var committer: User,
            @JsonProperty("html_url")
            var htmlUrl: String,
            @JsonProperty("node_id")
            var nodeId: String,
            @JsonProperty("parents")
            var parents: List<ParentCommit>,
            @JsonProperty("sha")
            var sha: String,
            @JsonProperty("url")
            var url: String
    ) {
        data class Commit(
                @JsonProperty("author")
                var author: Committer,
                @JsonProperty("comment_count")
                var commentCount: Int,
                @JsonProperty("committer")
                var committer: Committer,
                @JsonProperty("message")
                var message: String,
                @JsonProperty("tree")
                var tree: CommitTree,
                @JsonProperty("url")
                var url: String,
                @JsonProperty("verification")
                var verification: Verification
        ) {
            data class CommitTree(
                    @JsonProperty("sha")
                    var sha: String,
                    @JsonProperty("url")
                    var url: String
            )

            data class Verification(
                    @JsonProperty("payload")
                    var payload: Any?,
                    @JsonProperty("reason")
                    var reason: String,
                    @JsonProperty("signature")
                    var signature: Any?,
                    @JsonProperty("verified")
                    var verified: Boolean
            )
        }

        data class ParentCommit(
                @JsonProperty("html_url")
                var htmlUrl: String,
                @JsonProperty("sha")
                var sha: String,
                @JsonProperty("url")
                var url: String
        )
    }
}
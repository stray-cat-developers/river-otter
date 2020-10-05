package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.domain.topic.Topic
import java.time.LocalDateTime

class TopicResources {

    data class Request(
        val name: String
    )

    data class Reply(
        val id: String,
        val name: String,
        val createdAt: LocalDateTime,
    ) {
        companion object {
            fun from(topic: Topic): Reply {
                return topic.run {
                    Reply(
                        id.toString(),
                        name,
                        createdAt
                    )
                }
            }
        }
    }
}

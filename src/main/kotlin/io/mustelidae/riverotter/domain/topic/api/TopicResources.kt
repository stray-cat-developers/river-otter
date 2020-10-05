package io.mustelidae.riverotter.domain.topic.api

import java.time.LocalDateTime

class TopicResources {

    data class Reply(
        val id: String,
        val createdAt: LocalDateTime,
    )
}

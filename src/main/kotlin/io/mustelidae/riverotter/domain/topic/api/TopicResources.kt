package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.domain.topic.Topic
import java.time.LocalDateTime

class TopicResources {

    data class Request(
        val name: String,
        val code: String? = null
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

    data class ReplyWithCalendar(
        val id: String,
        val name: String,
        val createdAt: LocalDateTime,
        val calendars: List<TopicCalendarResources.Reply>
    ) {
        private constructor(reply: Reply, calendars: List<TopicCalendarResources.Reply>) : this (
            reply.id,
            reply.name,
            reply.createdAt,
            calendars
        )

        companion object {
            fun from(topic: Topic): ReplyWithCalendar {
                val reply = Reply.from(topic)
                val calendars = topic.calendars.map { TopicCalendarResources.Reply.from(it) }
                return ReplyWithCalendar(
                    reply,
                    calendars
                )
            }
        }
    }
}

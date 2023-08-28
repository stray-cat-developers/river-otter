package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.common.Replies
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.topic.TopicFinder
import io.mustelidae.riverotter.domain.topic.TopicInteraction
import io.mustelidae.riverotter.utils.toReplies
import io.mustelidae.riverotter.utils.toReply
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Topic", description = "Topic registration")
@RestController
@RequestMapping("topics")
class TopicController(
    private val topicInteraction: TopicInteraction,
    private val topicFinder: TopicFinder,
) {

    @Operation(summary = "Add topic")
    @PostMapping
    fun add(@RequestBody request: TopicResources.Request): Reply<String> {
        return topicInteraction.addBy(request.name, request.code)
            .toString()
            .toReply()
    }

    @Operation(summary = "Remove topic")
    @DeleteMapping("{id}")
    fun remove(
        @PathVariable id: String,
    ) {
        topicInteraction.remove(ObjectId(id))
    }

    @Operation(summary = "Find all topic")
    @GetMapping
    fun findAll(): Replies<TopicResources.Reply> {
        return topicFinder.findAll()
            .map { TopicResources.Reply.from(it) }
            .toReplies()
    }

    @Operation(summary = "Find topic")
    @GetMapping("{id}")
    fun find(
        @PathVariable id: String,
    ): Reply<TopicResources.ReplyWithCalendar> {
        val topic = topicFinder.findOrThrow(ObjectId(id))
        return TopicResources.ReplyWithCalendar.from(topic)
            .toReply()
    }
}

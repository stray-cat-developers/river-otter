package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.utils.fromJson
import io.mustelidae.riverotter.utils.toJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class TopicControllerFlow(
    private val mockMvc: MockMvc,
) {

    fun addTopic(name: String): String {
        val request = TopicResources.Request(
            name,
        )
        val uri = linkTo<TopicController> { add(request) }.toUri()
        return mockMvc.post(uri) {
            content = request.toJson()
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }.andReturn()
            .response
            .contentAsString
            .fromJson<Reply<String>>()
            .content!!
    }

    fun findTopic(topicId: String): TopicResources.ReplyWithCalendar {
        val uri = linkTo<TopicController> { find(topicId) }.toUri()
        return mockMvc.get(uri) {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }.andReturn()
            .response
            .contentAsString
            .fromJson<Reply<TopicResources.ReplyWithCalendar>>()
            .content!!
    }
}

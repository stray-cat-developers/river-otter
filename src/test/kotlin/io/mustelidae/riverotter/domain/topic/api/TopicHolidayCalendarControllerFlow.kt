package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources
import io.mustelidae.riverotter.utils.fromJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

internal class TopicHolidayCalendarControllerFlow(
    private val mockMvc: MockMvc,
) {

    fun getYear(topicId: String): HolidayCalendarResources.Reply.Calendar {
        val uri = linkTo<TopicHolidayCalendarController> { findYear(topicId, "KR", 2020) }.toUri()
        return mockMvc.get(uri) {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }.andReturn()
            .response
            .contentAsString
            .fromJson<Reply<HolidayCalendarResources.Reply.Calendar>>()
            .content!!
    }
}

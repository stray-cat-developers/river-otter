package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.common.Replies
import io.mustelidae.riverotter.utils.fromJson
import io.mustelidae.riverotter.utils.toJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

internal class HolidayCalendarControllerFlow(
    private val mockMvc: MockMvc
) {

    fun crawling(): List<HolidayCalendarResources.Reply.YearOfCountry> {
        val request = HolidayCalendarResources.Request.Crawling(
            2020,
            "KR"
        )
        val uri = linkTo<HolidayCalendarController> { crawling(request) }.toUri()

        return mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }.andReturn()
            .response
            .contentAsString
            .fromJson<Replies<HolidayCalendarResources.Reply.YearOfCountry>>()
            .getContent()
            .toList()
    }
}

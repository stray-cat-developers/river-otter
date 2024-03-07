package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.utils.toJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post
import java.time.LocalDate

class HolidayCalendarMaintenanceFlow(
    private val mockMvc: MockMvc,
) {

    fun addHoliday(date: LocalDate, country: String) {
        val request = HolidayCalendarResources.Request.Holiday(
            date,
            "Test Hoiday",
            Holiday.Type.SCHEDULE_HOLIDAY,
            "sample",
        )

        val uri = linkTo<HolidayCalendarMaintenanceController> { addHoliday(request, country) }.toUri()

        mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    fun removeHoliday(date: LocalDate, country: String) {
        val year = date.year
        val month = date.monthValue
        val day = date.dayOfMonth
        val uri = linkTo<HolidayCalendarMaintenanceController> { removeHoliday(country, year, month, day) }.toUri()

        mockMvc.delete(uri) {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }
}

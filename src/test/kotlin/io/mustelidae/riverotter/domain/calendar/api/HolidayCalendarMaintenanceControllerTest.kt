package io.mustelidae.riverotter.domain.calendar.api

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mustelidae.riverotter.config.FlowTestSupport
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

class HolidayCalendarMaintenanceControllerTest : FlowTestSupport() {

    @Test
    fun addHoliday() {
        val holidayCalendarMaintenanceFlow = HolidayCalendarMaintenanceFlow(mockMvc)
        val holidayCalendarControllerFlow = HolidayCalendarControllerFlow(mockMvc)
        val testDate = LocalDate.of(2020, 6, 30)
        val country = "KR"

        holidayCalendarMaintenanceFlow.addHoliday(testDate, country)

        val calendar = holidayCalendarControllerFlow.find(country, 2020)

        val holiday = calendar.holidays.find { it.date == testDate }

        holiday shouldNotBe null
    }

    @Test
    fun removeHoliday() {
        val country = "KR"
        val holidayCalendarMaintenanceFlow = HolidayCalendarMaintenanceFlow(mockMvc)
        val holidayCalendarControllerFlow = HolidayCalendarControllerFlow(mockMvc)
        val testDate = LocalDate.of(2020, 12, 25)

        holidayCalendarMaintenanceFlow.removeHoliday(testDate, country)

        val calendar = holidayCalendarControllerFlow.find(country, 2020)

        val holiday = calendar.holidays.find { it.date == testDate }

        holiday shouldBe null
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll(
            @Autowired holidayCalendarController: HolidayCalendarController,
        ) {
            holidayCalendarController.crawling(HolidayCalendarResources.Request.Crawling(2020))
        }
    }
}

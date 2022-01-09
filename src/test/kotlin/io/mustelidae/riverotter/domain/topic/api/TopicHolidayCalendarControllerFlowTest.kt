package io.mustelidae.riverotter.domain.topic.api

import io.kotlintest.shouldBe
import io.mustelidae.riverotter.config.FlowTestSupport
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarControllerFlow
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class TopicHolidayCalendarControllerFlowTest : FlowTestSupport() {

    @Test
    fun workOnSunday() {
        // Given
        val topicControllerFlow = TopicControllerFlow(mockMvc)
        val topicScheduleControllerFlow = TopicScheduleControllerFlow(mockMvc)
        val topicHolidayCalendarControllerFlow = TopicHolidayCalendarControllerFlow(mockMvc)
        val holidayCalendarControllerFlow = HolidayCalendarControllerFlow(mockMvc)
        val topicId = topicControllerFlow.addTopic("sunDay")
        val workSchedule = TopicResources.WorkSchedule(
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = false,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = false,
                is24Hours = false
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = true
            )
        )
        topicScheduleControllerFlow.addWorkSchedule(topicId, workSchedule)

        // When
        val yearOfCountries = holidayCalendarControllerFlow.crawling()
        val reply = topicHolidayCalendarControllerFlow.getYear(topicId)

        reply.holidays.find { it.date.dayOfWeek == DayOfWeek.SUNDAY } shouldBe null
    }
}

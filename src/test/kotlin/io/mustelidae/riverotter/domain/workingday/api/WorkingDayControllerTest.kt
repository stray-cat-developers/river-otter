package io.mustelidae.riverotter.domain.workingday.api

import io.kotest.matchers.shouldBe
import io.mustelidae.riverotter.config.IntegrationSupport
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarController
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources
import io.mustelidae.riverotter.domain.topic.Topic
import io.mustelidae.riverotter.domain.topic.aFixture
import io.mustelidae.riverotter.domain.topic.api.TopicCalendarController
import io.mustelidae.riverotter.domain.topic.api.TopicCalendarResources
import io.mustelidae.riverotter.domain.topic.repository.TopicRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorkingDayControllerTest : IntegrationSupport() {

    @Autowired
    private lateinit var workingDayController: WorkingDayController

    @Autowired
    private lateinit var holidayCalendarController: HolidayCalendarController

    @Autowired
    private lateinit var topicCalendarController: TopicCalendarController

    @Autowired
    private lateinit var topicRepository: TopicRepository

    private lateinit var topicId: String
    private val country = "KR"

    @BeforeAll
    fun beforeAll() {
        val topic = Topic.aFixture()
        topicRepository.save(topic)
        topicId = topic.id.toString()

        holidayCalendarController.crawling(HolidayCalendarResources.Request.Crawling(2020, country))
        holidayCalendarController.crawling(HolidayCalendarResources.Request.Crawling(2021, country))
    }

    @Test
    fun calculateWorkingDayUsingCountryHoliday() {
        // Given
        val date = LocalDate.of(2020, 12, 25)
        val businessDays = 7

        // When
        val workingDay = workingDayController.calculateWorkingDayUsingCountryHoliday(country, date, businessDays).content!!
        // Then
        workingDay shouldBe LocalDate.of(2021, 1, 6)
    }

    @Test
    fun calculateWorkingDayUsingTopicHoliday() {
        // Given
        val date = LocalDate.of(2020, 12, 25)
        val businessDays = 7

        run {
            // Given
            val request = TopicCalendarResources.Request.TopicHoliday(
                LocalDate.of(2021, 1, 4),
                "fennec is free!",
            )

            // When
            topicCalendarController.addHoliday(topicId, country, request)
        }

        // When
        val workingDay = workingDayController.calculateWorkingDayUsingTopicHoliday(topicId, country, date, businessDays).content!!

        // Then
        workingDay shouldBe LocalDate.of(2021, 1, 7)
    }
}

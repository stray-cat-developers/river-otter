package io.mustelidae.riverotter.domain.topic.api

import io.kotlintest.matchers.asClue
import io.kotlintest.shouldBe
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarController
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.config.IntegrationSupport
import io.mustelidae.riverotter.domain.topic.Workday
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TopicHolidayCalendarControllerTest : IntegrationSupport() {

    @Autowired
    private lateinit var topicHolidayCalendarController: TopicHolidayCalendarController
    @Autowired
    private lateinit var holidayCalendarController: HolidayCalendarController
    @Autowired
    private lateinit var topicController: TopicController
    @Autowired
    private lateinit var topicCalendarController: TopicCalendarController

    private val year = 2020
    private val country = "KR"

    private lateinit var topicId: String

    @BeforeAll
    fun beforeAll() {
        holidayCalendarController.crawling(HolidayCalendarResources.Request.Crawling(year, country))
        topicId = topicController.add(TopicResources.Request("Test Topic")).content!!
    }

    @Test
    fun findYear() {
        // Given
        val topicHoliday = LocalDate.of(year, 1, 2)
        val topicWorkday = LocalDate.of(year, 1, 1)

        run {
            topicCalendarController.addHoliday(
                topicId,
                country,
                TopicCalendarResources.Request.TopicHoliday(
                    topicHoliday,
                    "Topic Holiday",
                    "Party time!"
                )
            )

            topicCalendarController.addWorkday(
                topicId,
                country,
                TopicCalendarResources.Request.TopicWorkday(
                    topicWorkday,
                    "Topic workday",
                    Workday.Type.FORCE_WORK,
                    "Work time :("
                )
            )
        }

        // When
        val calendar = topicHolidayCalendarController.findYear(topicId, country, year).content!!
        // Then
        val partyTimeHoliday = calendar.holidays.find { it.date == topicHoliday }

        partyTimeHoliday!!.asClue {
            it.date shouldBe topicHoliday
            it.day shouldBe 2
            it.month shouldBe 1
            it.type shouldBe Holiday.Type.TOPIC_HOLIDAY
        }

        val workTime = calendar.holidays.find { it.date == topicWorkday }

        workTime shouldBe null
    }

    @Test
    fun findMonth() {
        // Given
        val month = 3
        val topicWorkday = LocalDate.of(year, month, 1)

        run {
            topicCalendarController.addWorkday(
                topicId,
                country,
                TopicCalendarResources.Request.TopicWorkday(
                    topicWorkday,
                    "Topic workday",
                    Workday.Type.FORCE_WORK,
                    "Work time :("
                )
            )
        }

        // When
        val holidays = topicHolidayCalendarController.findMonth(topicId, country, year, month).getContent()
        // Then
        val independenceMovementDay = holidays.find { it.date == topicWorkday }

        independenceMovementDay shouldBe null
    }

    @Test
    fun findDay() {
        // Given
        val month = 12
        val day = 24

        val topicHoliday = LocalDate.of(year, month, day)

        run {
            topicCalendarController.addHoliday(
                topicId,
                country,
                TopicCalendarResources.Request.TopicHoliday(
                    topicHoliday,
                    "Topic Holiday",
                    "Party time!"
                )
            )
        }
        // When
        val holiday = topicHolidayCalendarController.findDay(topicId, country, year, month, day).content!!
        // Then
        holiday.asClue {
            it.isHoliday shouldBe true
            it.holiday!!.date shouldBe topicHoliday
            it.holiday!!.day shouldBe day
            it.holiday!!.month shouldBe month
            it.holiday!!.type shouldBe Holiday.Type.TOPIC_HOLIDAY
        }
    }
}

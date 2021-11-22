package io.mustelidae.riverotter.domain.topic.api

import io.kotlintest.shouldBe
import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.domain.config.IntegrationSupport
import io.mustelidae.riverotter.domain.topic.Topic
import io.mustelidae.riverotter.domain.topic.Workday
import io.mustelidae.riverotter.domain.topic.aFixture
import io.mustelidae.riverotter.domain.topic.repository.TopicRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class TopicCalendarControllerTest : IntegrationSupport() {

    @Autowired
    private lateinit var topicCalendarController: TopicCalendarController
    @Autowired
    private lateinit var topicRepository: TopicRepository

    private val country = "KR"
    private val year = 2020
    private val locale = AvailableCountry.getLocale(country)
    private lateinit var topicId: String

    @BeforeAll()
    fun beforeAll() {
        val topic = Topic.aFixture()
        topicRepository.save(topic)
        topicId = topic.id.toString()
    }

    @Test
    @Order(1)
    fun addHoliday() {
        // Given
        val request = TopicCalendarResources.Request.TopicHoliday(
            LocalDate.of(year, 1, 1),
            "fennec is free!"
        )

        // When
        topicCalendarController.addHoliday(topicId, country, request)

        // Then
        val topic = topicRepository.findByIdOrNull(ObjectId(topicId))!!
        val calendar = topic.getCalendar(locale, year)!!
        calendar.holidays.size shouldBe 1
        calendar.holidays.first().date shouldBe request.date
    }

    @Test
    @Order(2)
    fun removeHoliday() {
        val date = LocalDate.of(year, 1, 1)
        topicCalendarController.removeHoliday(topicId, country, date)

        // Then
        val topic = topicRepository.findByIdOrNull(ObjectId(topicId))!!
        val calendar = topic.getCalendar(locale, year)!!
        calendar.holidays.size shouldBe 0
    }

    @Test
    @Order(3)
    fun addWorkday() {
        // Given
        val request = TopicCalendarResources.Request.TopicWorkday(
            LocalDate.of(year, 1, 1),
            "fennec is free!",
            Workday.Type.FORCE_WORK
        )
        // When
        topicCalendarController.addWorkday(topicId, country, request)
    }

    @Test
    @Order(4)
    fun removeWorkday() {
        val date = LocalDate.of(year, 1, 1)
        topicCalendarController.removeWorkday(topicId, country, date)

        // Then
        val topic = topicRepository.findByIdOrNull(ObjectId(topicId))!!
        val calendar = topic.getCalendar(locale, year)!!
        calendar.workdays.size shouldBe 0
    }
}

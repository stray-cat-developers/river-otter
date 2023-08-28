package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.domain.topic.repository.TopicCalendarRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Locale

@Service
class TopicCalendarInteraction(
    private val topicCalendarFinder: TopicCalendarFinder,
    private val topicCalendarRepository: TopicCalendarRepository,
) {

    fun add(locale: Locale, year: Int): ObjectId {
        val topicCalendar = TopicCalendar(locale, year)
        return topicCalendarRepository.save(topicCalendar).id
    }

    fun remove(id: ObjectId) {
        topicCalendarRepository.deleteById(id)
    }

    fun addHoliday(id: ObjectId, date: LocalDate, name: String, description: String? = null) {
        val topicCalendar = topicCalendarFinder.findOrThrow(id)
        val holiday = Holiday(date, name, Holiday.Type.TOPIC_HOLIDAY, description)

        topicCalendar.addBy(holiday)
        topicCalendarRepository.save(topicCalendar)
    }

    fun addWorkday(id: ObjectId, date: LocalDate, name: String, type: Workday.Type, description: String? = null) {
        val topicCalendar = topicCalendarFinder.findOrThrow(id)
        val workday = Workday(date, name, type, description)

        topicCalendar.addBy(workday)
        topicCalendarRepository.save(topicCalendar)
    }

    fun removeHoliday(id: ObjectId, date: LocalDate) {
        val topicCalendar = topicCalendarFinder.findOrThrow(id)
        topicCalendar.removeHoliday(date)
        topicCalendarRepository.save(topicCalendar)
    }

    fun removeWorkday(id: ObjectId, date: LocalDate) {
        val topicCalendar = topicCalendarFinder.findOrThrow(id)
        topicCalendar.removeWorkDay(date)
        topicCalendarRepository.save(topicCalendar)
    }
}

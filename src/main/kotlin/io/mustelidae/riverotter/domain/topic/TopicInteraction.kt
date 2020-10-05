package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.domain.topic.repository.TopicRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Locale

@Service
class TopicInteraction(
    private val topicFinder: TopicFinder,
    private val topicRepository: TopicRepository,
    private val topicCalendarInteraction: TopicCalendarInteraction
) {

    fun addBy(name: String): ObjectId {
        val topic = Topic(name)
        return topicRepository.save(topic).id
    }

    fun remove(id: ObjectId) {
        val topic = topicFinder.findOrThrow(id)
        topicRepository.deleteById(id)
    }

    fun addHoliday(id: ObjectId, locale: Locale, date: LocalDate, name: String, description: String? = null) {
        val topic = topicFinder.findOrThrow(id)
        topic.addCalendarIfNotExist(locale, date.year)
        val calendar = topic.getCalendar(locale, date.year)!!
        topicCalendarInteraction.addHoliday(calendar.id, date, name, description)
    }

    fun removeHoliday(id: ObjectId, locale: Locale, date: LocalDate) {
        val topic = topicFinder.findOrThrow(id)
        val calendar = topic.getCalendar(locale, date.year)
        if (calendar != null) {
            topicCalendarInteraction.removeHoliday(calendar.id, date)
        }
    }

    fun addWorkday(id: ObjectId, locale: Locale, date: LocalDate, name: String, type: Workday.Type, description: String? = null) {
        val topic = topicFinder.findOrThrow(id)
        topic.addCalendarIfNotExist(locale, date.year)
        val calendar = topic.getCalendar(locale, date.year)!!
        topicCalendarInteraction.addWorkday(calendar.id, date, name, type, description)
    }

    fun removeWorkday(id: ObjectId, locale: Locale, date: LocalDate) {
        val topic = topicFinder.findOrThrow(id)
        val calendar = topic.getCalendar(locale, date.year)
        if (calendar != null) {
            topicCalendarInteraction.removeWorkday(calendar.id, date)
        }
    }
}

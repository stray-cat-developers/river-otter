package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.domain.topic.repository.TopicRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Locale

@Service
class TopicInteraction(
    private val topicFinder: TopicFinder,
    private val topicCalendarFinder: TopicCalendarFinder,
    private val topicRepository: TopicRepository,
    private val topicCalendarInteraction: TopicCalendarInteraction
) {

    fun addBy(name: String, code: String?): ObjectId {
        val id = if (code == null)
            ObjectId()
        else
            ObjectId(code)

        val topic = Topic(id, name)
        return topicRepository.save(topic).id
    }

    fun remove(id: ObjectId) {
        topicRepository.deleteById(id)
    }

    fun addHoliday(id: ObjectId, locale: Locale, date: LocalDate, name: String, description: String? = null) {
        val topic = topicFinder.findOrThrow(id)
        val calendarId = addCalendarIfNotExist(topic, locale, date)
        topicCalendarInteraction.addHoliday(calendarId, date, name, description)
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
        val calendarId = addCalendarIfNotExist(topic, locale, date)
        topicCalendarInteraction.addWorkday(calendarId, date, name, type, description)
    }

    fun removeWorkday(id: ObjectId, locale: Locale, date: LocalDate) {
        val topic = topicFinder.findOrThrow(id)
        val calendar = topic.getCalendar(locale, date.year)
        if (calendar != null) {
            topicCalendarInteraction.removeWorkday(calendar.id, date)
        }
    }

    private fun addCalendarIfNotExist(
        topic: Topic,
        locale: Locale,
        date: LocalDate
    ): ObjectId {
        val hasCalendar = topic.hasCalendar(locale, date.year)

        return if (hasCalendar.not()) {
            val calendarId = topicCalendarInteraction.add(locale, date.year)
            val calendar = topicCalendarFinder.findOrThrow(calendarId)
            topic.addBy(calendar)
            topicRepository.save(topic)

            calendarId
        } else {
            topic.getCalendar(locale, date.year)!!.id
        }
    }
}

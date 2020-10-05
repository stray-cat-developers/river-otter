package io.mustelidae.riverotter.domain.topic.repository

import io.mustelidae.riverotter.domain.topic.TopicCalendar
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Locale

interface TopicCalendarRepository : MongoRepository<TopicCalendar, ObjectId> {
    fun findByYearAndLocale(year: Int, locale: Locale)
}

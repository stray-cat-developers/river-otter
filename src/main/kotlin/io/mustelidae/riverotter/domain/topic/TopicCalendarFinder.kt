package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.config.DataNotFoundException
import io.mustelidae.riverotter.domain.topic.repository.TopicCalendarRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TopicCalendarFinder(
    private val topicCalendarRepository: TopicCalendarRepository,
) {

    fun findOrThrow(id: ObjectId): TopicCalendar {
        return topicCalendarRepository.findByIdOrNull(id) ?: throw DataNotFoundException("topic calendar id($id) not found")
    }
}

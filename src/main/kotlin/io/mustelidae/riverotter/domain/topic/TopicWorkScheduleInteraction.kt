package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.config.DataNotFoundException
import io.mustelidae.riverotter.config.DuplicateDataException
import io.mustelidae.riverotter.domain.topic.api.TopicResources
import io.mustelidae.riverotter.domain.topic.repository.TopicRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
class TopicWorkScheduleInteraction(
    private val topicFinder: TopicFinder,
    private val topicCalendarFinder: TopicCalendarFinder,
    private val topicRepository: TopicRepository
) {

    fun add(topicId: ObjectId, request: TopicResources.WorkSchedule) {
        val topic = topicFinder.findOrThrow(topicId)
        if (topic.workSchedule != null)
            throw DuplicateDataException("The topic already has a schedule.")

        topic.workSchedule = WorkSchedule.from(request)
        topicRepository.save(topic)
    }

    fun modify(
        topicId: ObjectId,
        dayOfWeek: DayOfWeek,
        request: TopicResources.WorkSchedule.Schedule
    ) {
        val topic = topicFinder.findOrThrow(topicId)
        if (topic.workSchedule == null)
            throw DataNotFoundException("Please register your weekly schedule first.")

        val workTime = WorkSchedule.WorkTime.from(request)

        when (dayOfWeek) {
            DayOfWeek.MONDAY -> topic.workSchedule!!.mon = workTime
            DayOfWeek.TUESDAY -> topic.workSchedule!!.tue = workTime
            DayOfWeek.WEDNESDAY -> topic.workSchedule!!.wed = workTime
            DayOfWeek.THURSDAY -> topic.workSchedule!!.thu = workTime
            DayOfWeek.FRIDAY -> topic.workSchedule!!.fri = workTime
            DayOfWeek.SATURDAY -> topic.workSchedule!!.sat = workTime
            DayOfWeek.SUNDAY -> topic.workSchedule!!.sun = workTime
        }
    }

    fun remove(
        topicId: String
    ) {
        val topic = topicFinder.findOrThrow(ObjectId(topicId))
        topic.workSchedule = null
        topicRepository.save(topic)
    }
}

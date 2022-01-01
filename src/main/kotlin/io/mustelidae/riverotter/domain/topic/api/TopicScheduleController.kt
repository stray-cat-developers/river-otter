package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.topic.TopicWorkScheduleInteraction
import io.mustelidae.riverotter.utils.toReply
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.DayOfWeek

@Api(tags = ["Topic calendar"], description = "Topic custom calendar")
@RestController
@RequestMapping("topics/{id}")
class TopicScheduleController(
    private val topicWorkScheduleInteraction: TopicWorkScheduleInteraction
) {

    @ApiOperation("Add work schedule to topic")
    @PostMapping("work-schedule")
    fun addSchedule(
        @PathVariable id: String,
        @RequestBody request: TopicResources.WorkSchedule
    ): Reply<Unit> {
        val topicId = ObjectId(id)
        topicWorkScheduleInteraction.add(topicId, request)
        return Unit.toReply()
    }

    @ApiOperation("Add work schedule to topic")
    @PostMapping("work-schedule/{dayOfWeek}")
    fun modifySchedule(
        @PathVariable id: String,
        @ApiParam(value = "ex) MONDAY, TUESDAY, WEDNESDAY ...")
        @PathVariable dayOfWeek: String,
        @RequestBody request: TopicResources.WorkSchedule.Schedule
    ): Reply<Unit> {
        val topicId = ObjectId(id)
        val dow = DayOfWeek.valueOf(dayOfWeek.uppercase())

        topicWorkScheduleInteraction.modify(topicId, dow, request)
        return Unit.toReply()
    }
}

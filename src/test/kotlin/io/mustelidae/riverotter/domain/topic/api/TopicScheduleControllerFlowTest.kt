package io.mustelidae.riverotter.domain.topic.api

import io.kotlintest.matchers.asClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mustelidae.riverotter.config.FlowTestSupport
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

internal class TopicScheduleControllerFlowTest : FlowTestSupport() {

    @Test
    fun addWorkSchedule() {
        // Given
        val topicControllerFlow = TopicControllerFlow(mockMvc)
        val topicScheduleControllerFlow = TopicScheduleControllerFlow(mockMvc)

        val request = topicScheduleControllerFlow.getRequestWorkSchedule()
        val topicName = "schedule Test"
        val topicId = topicControllerFlow.addTopic(topicName)

        // When
        topicScheduleControllerFlow.addWorkSchedule(topicId, request)
        // Then
        val reply = topicControllerFlow.findTopic(topicId)

        reply.asClue {
            it.name shouldBe topicName
        }

        reply.workSchedule shouldNotBe null
        reply.workSchedule!!.asClue {
            it.sun.isOn shouldBe request.sun.isOn
            it.sun.is24Hours shouldBe request.sun.is24Hours
            it.sun.startTime shouldBe request.sun.startTime
            it.sun.endTime shouldBe request.sun.endTime

            it.mon.isOn shouldBe request.mon.isOn
            it.mon.is24Hours shouldBe request.mon.is24Hours
            it.mon.startTime shouldBe request.mon.startTime
            it.mon.endTime shouldBe request.mon.endTime
        }
    }

    @Test
    fun modifySchedule() {
        // Given
        val topicControllerFlow = TopicControllerFlow(mockMvc)
        val topicScheduleControllerFlow = TopicScheduleControllerFlow(mockMvc)

        val request = topicScheduleControllerFlow.getRequestWorkSchedule()
        val topicName = "schedule modify Test"
        val topicId = topicControllerFlow.addTopic(topicName)
        topicScheduleControllerFlow.addWorkSchedule(topicId, request)
        val mondaySchedule = TopicResources.WorkSchedule.Schedule(
            isOn = false,
            is24Hours = false
        )

        // When
        topicScheduleControllerFlow.modifySchedule(topicId, DayOfWeek.MONDAY, mondaySchedule)

        // Then
        val reply = topicControllerFlow.findTopic(topicId)

        reply.workSchedule shouldNotBe null
        reply.workSchedule!!.mon.asClue {
            it.isOn shouldBe mondaySchedule.isOn
            it.is24Hours shouldBe mondaySchedule.is24Hours
            it.endTime shouldBe mondaySchedule.endTime
            it.startTime shouldBe mondaySchedule.startTime
        }
    }
}

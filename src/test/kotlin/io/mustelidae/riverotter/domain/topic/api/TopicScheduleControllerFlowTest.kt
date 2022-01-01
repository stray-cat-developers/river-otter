package io.mustelidae.riverotter.domain.topic.api

import io.kotlintest.matchers.asClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mustelidae.riverotter.domain.config.FlowTestSupport
import io.mustelidae.riverotter.flow.topic.TopicControllerFlow
import io.mustelidae.riverotter.flow.topic.TopicScheduleControllerFlow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TopicScheduleControllerFlowTest : FlowTestSupport() {

    private val topicControllerFlow = TopicControllerFlow(mockMvc)
    private val topicScheduleControllerFlow = TopicScheduleControllerFlow(mockMvc)

    @Test
    fun addWorkSchedule() {
        // Given
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
}

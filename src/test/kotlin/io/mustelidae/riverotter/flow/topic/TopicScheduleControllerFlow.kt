package io.mustelidae.riverotter.flow.topic

import io.mustelidae.riverotter.domain.topic.api.TopicResources
import io.mustelidae.riverotter.domain.topic.api.TopicScheduleController
import io.mustelidae.riverotter.utils.toJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalTime

class TopicScheduleControllerFlow(
    private val mockMvc: MockMvc
) {

    fun getRequestWorkSchedule(): TopicResources.WorkSchedule {
        return TopicResources.WorkSchedule(
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = false,
                startTime = LocalTime.of(9, 0, 0),
                endTime = LocalTime.of(23, 0, 0)
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = false,
                is24Hours = false
            ),
            TopicResources.WorkSchedule.Schedule(
                isOn = true,
                is24Hours = true
            )
        )
    }

    fun addWorkSchedule(topicId: String, request: TopicResources.WorkSchedule) {

        val uri = linkTo<TopicScheduleController> { addSchedule(topicId, request) }.toUri()

        mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }
}

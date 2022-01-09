package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.utils.toJson
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.DayOfWeek
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

        val uri = linkTo<TopicScheduleController> { add(topicId, request) }.toUri()

        mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    fun modifySchedule(topicId: String, dayOfWeek: DayOfWeek, request: TopicResources.WorkSchedule.Schedule) {

        val uri = linkTo<TopicScheduleController> { modify(topicId, dayOfWeek.toString(), request) }.toUri()

        mockMvc.put(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = request.toJson()
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }
}

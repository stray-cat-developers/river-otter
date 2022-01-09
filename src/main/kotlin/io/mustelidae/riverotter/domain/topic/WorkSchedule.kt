package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.domain.topic.api.TopicResources
import java.time.LocalTime

class WorkSchedule(
    var mon: WorkTime,
    var tue: WorkTime,
    var wed: WorkTime,
    var thu: WorkTime,
    var fri: WorkTime,
    var sat: WorkTime,
    var sun: WorkTime
) {

    data class WorkTime(
        val isOn: Boolean,
        val is24Hours: Boolean,
        val startTime: LocalTime? = null,
        val endTime: LocalTime? = null
    ) {
        companion object {
            fun from(request: TopicResources.WorkSchedule.Schedule): WorkTime {
                return if (request.isOn.not())
                    WorkTime(isOn = false, is24Hours = false)
                else
                    WorkTime(
                        true,
                        request.is24Hours,
                        request.startTime,
                        request.endTime
                    )
            }
        }
    }

    companion object {
        fun from(request: TopicResources.WorkSchedule): WorkSchedule {
            return request.run {
                WorkSchedule(
                    WorkTime.from(mon),
                    WorkTime.from(tue),
                    WorkTime.from(wed),
                    WorkTime.from(thu),
                    WorkTime.from(fri),
                    WorkTime.from(sat),
                    WorkTime.from(sun),
                )
            }
        }
    }
}

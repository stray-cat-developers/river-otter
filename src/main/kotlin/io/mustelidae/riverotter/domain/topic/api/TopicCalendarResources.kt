package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources
import io.mustelidae.riverotter.domain.topic.TopicCalendar
import io.mustelidae.riverotter.domain.topic.Workday
import io.swagger.annotations.ApiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

class TopicCalendarResources {

    @ApiModel("TopicCalendar.Reply")
    data class Reply(
        val id: String,
        val locale: Locale,
        val year: Int,
        val createdAt: LocalDateTime,
        val modifiedAt: LocalDateTime,
        val holidays: List<HolidayCalendarResources.Reply.HolidayOfCountry>,
        val workdays: List<WorkdayOfTopic>
    ) {
        companion object {
            fun from(topicCalendar: TopicCalendar): Reply {
                val locale = topicCalendar.locale
                val holidays = topicCalendar.holidays.map { HolidayCalendarResources.Reply.HolidayOfCountry.from(it, locale) }
                val workdays = topicCalendar.workdays.map { WorkdayOfTopic.from(it, locale) }

                return Reply(
                    topicCalendar.id.toString(),
                    locale,
                    topicCalendar.year,
                    topicCalendar.createdAt,
                    topicCalendar.modifiedAt,
                    holidays,
                    workdays
                )
            }
        }
    }

    @ApiModel("TopicCalendar.WorkdayOfTopic")
    data class WorkdayOfTopic(
        val date: LocalDate,
        val month: Int,
        val day: Int,
        val name: String,
        val type: Workday.Type,
        val dayOfWeek: String,
        val description: String? = null
    ) {
        companion object {
            fun from(workday: Workday, locale: Locale): WorkdayOfTopic {
                return workday.run {
                    WorkdayOfTopic(
                        date,
                        date.monthValue,
                        date.dayOfMonth,
                        name,
                        type,
                        date.dayOfWeek.getDisplayName(TextStyle.FULL, locale),
                        description
                    )
                }
            }
        }
    }
}

package io.mustelidae.riverotter.domain.topic.synchronization

import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.domain.topic.TopicCalendar
import io.mustelidae.riverotter.domain.topic.WorkSchedule
import java.time.LocalDate

class DayTopicHolidaySynchronization(
    val date: LocalDate,
    countryHoliday: Holiday?
) : TopicHolidaySynchronization {

    private var topicHoliday: Holiday? = countryHoliday

    override fun syncWorkSchedule(workSchedule: WorkSchedule) {
        topicHoliday = makeHolidayOwingToWorkSchedule(date, workSchedule)
    }

    override fun syncTopicCalendar(topicCalendar: TopicCalendar) {
        topicCalendar.holidays.find { it.date == date }?.let {
            topicHoliday = it
        }

        topicCalendar.workdays.find { it.date == date }?.let {
            topicHoliday = null
        }
    }

    fun getHoliday(): Holiday? = topicHoliday
}

package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendar
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendarFinder
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Locale

@Service
class TopicHolidayCalendarFinder(
    private val topicFinder: TopicFinder,
    private val holidayCalendarFinder: HolidayCalendarFinder
) {

    fun findBy(id: ObjectId, locale: Locale, year: Int): HolidayCalendar {
        val topicCalendar = topicFinder.findOrThrow(id)
            .getCalendar(locale, year)
        val countryHolidayCalendar = holidayCalendarFinder.findOrThrow(locale, year)

        val holidayMap = countryHolidayCalendar.holidays.map { it.date to it }
            .toMap()
            .toMutableMap()

        topicCalendar?.let { calendar ->
            holidayMap.putAll(
                calendar.holidays.map { it.date to it }.toMap()
            )

            val workdays = calendar.workdays.map { it.date }

            workdays.forEach {
                holidayMap.remove(it)
            }
        }

        return HolidayCalendar(locale, year, holidayMap.values.toList())
    }

    fun findBy(id: ObjectId, locale: Locale, year: Int, month: Int): List<Holiday> {
        val holidayCalendar = findBy(id, locale, year)
        return holidayCalendar.holidays.filter { it.date.monthValue == month }
    }

    fun findBy(id: ObjectId, locale: Locale, year: Int, month: Int, day: Int): Holiday? {
        val date = LocalDate.of(year, month, day)
        val topicCalendar = topicFinder.findOrThrow(id)
            .getCalendar(locale, year)

        var holiday = holidayCalendarFinder.findBy(locale, year, month, day)

        if (topicCalendar != null) {
            topicCalendar.holidays.find { it.date == date }?.let {
                holiday = it
            }

            topicCalendar.workdays.find { it.date == date }?.let {
                holiday = null
            }
        }

        return holiday
    }
}

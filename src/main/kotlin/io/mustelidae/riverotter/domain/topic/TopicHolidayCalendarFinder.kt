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

        val holidayMap = countryHolidayCalendar.holidays.associateBy { it.date }
            .toMutableMap()

        if(topicCalendar != null) {
            // 토픽의 휴일도 합친다.
            holidayMap.putAll(
                topicCalendar.holidays.associateBy { it.date }
            )

            // 토픽이 일하는 날짜는 모두 휴일에서 제거한다.
            topicCalendar.workdays.map { it.date }
                .forEach {
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

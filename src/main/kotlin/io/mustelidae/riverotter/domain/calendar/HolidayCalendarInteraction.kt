package io.mustelidae.riverotter.domain.calendar

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendar
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendarFinder
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCrawler
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Locale

@Service
class HolidayCalendarInteraction(
    private val environment: AppEnvironment,
    private val holidayCalendarFinder: HolidayCalendarFinder,
    private val holidayCrawler: HolidayCrawler,
    private val yearTableInteraction: YearTableInteraction
) {

    fun findBy(locale: Locale, year: Int): HolidayCalendar {
        return holidayCalendarFinder.findOrThrow(year, locale)
    }

    fun findBy(locale: Locale, year: Int, month: Int): List<Holiday> {
        val holidayCalendar = findBy(locale, year)
        return holidayCalendar.holidays.filter { it.date.monthValue == month }
    }

    fun findBy(locale: Locale, year: Int, month: Int, day: Int): Holiday? {
        val holidayCalendar = findBy(locale, year)
        val time = LocalDate.of(year, month, day).atStartOfDay().toEpochSecond(ZoneOffset.UTC)

        val index = holidayCalendar.holidays.binarySearch { timeComparison(it, time) }
        return if (index >= 0)
            holidayCalendar.holidays[index]
        else
            null
    }

    private fun timeComparison(holiday: Holiday, time: Long) = (holiday.time - time).toInt()

    fun crawling(year: Int): List<Pair<Locale, ObjectId>> {
        val locales = environment.getAvailableLocales()

        return locales.map {
            crawling(year, it)
        }
    }

    fun crawling(year: Int, locale: Locale): Pair<Locale, ObjectId> {
        val id = holidayCrawler.crawling(year, locale)
        yearTableInteraction.save(locale, year, id)
        return Pair(locale, id)
    }
}

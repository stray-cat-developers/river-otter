package io.mustelidae.riverotter.domain.calender

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.calender.holiday.Holiday
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalenderFinder
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCrawler
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Locale

@Service
class HolidayCalenderInteraction(
    private val environment: AppEnvironment,
    private val holidayCalenderFinder: HolidayCalenderFinder,
    private val holidayCrawler: HolidayCrawler
) {

    fun findBy(locale: Locale, year: Int): HolidayCalender {
        return holidayCalenderFinder.findOrThrow(year, locale)
    }

    fun findBy(locale: Locale, year: Int, month: Int): List<Holiday> {
        val holidayCalender = findBy(locale, year)
        return holidayCalender.holidays.filter { it.date.monthValue == month }
    }

    fun findBy(locale: Locale, year: Int, month: Int, day: Int): Holiday? {
        val holidayCalender = findBy(locale, year)
        val time = LocalDate.of(year, month, day).atStartOfDay().toEpochSecond(ZoneOffset.UTC)

        val index = holidayCalender.holidays.binarySearch { timeComparison(it, time) }
        return if (index >= 0)
            holidayCalender.holidays[index]
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
        return Pair(locale, id)
    }
}

package io.mustelidae.riverotter.domain.calender

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalenderFinder
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCrawler
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class HolidayCalenderInteraction(
    private val environment: AppEnvironment,
    private val holidayCalenderFinder: HolidayCalenderFinder,
    private val holidayCrawler: HolidayCrawler
) {

    fun findYear(year: Int, locale: Locale): HolidayCalender {
        return holidayCalenderFinder.findOrThrow(year, locale)
    }

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

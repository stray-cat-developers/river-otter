package io.mustelidae.riverotter.domain.calendar

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCrawler
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class HolidayCalendarInteraction(
    private val environment: AppEnvironment,
    private val holidayCrawler: HolidayCrawler,
    private val yearTableInteraction: YearTableInteraction
) {

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

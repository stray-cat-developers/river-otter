package io.mustelidae.riverotter.domain.calendar

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendarFinder
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCrawler
import io.mustelidae.riverotter.domain.calendar.holiday.repository.HolidayCalendarRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Locale

@Service
class HolidayCalendarInteraction(
    private val environment: AppEnvironment,
    private val holidayCrawler: HolidayCrawler,
    private val yearTableInteraction: YearTableInteraction,
    private val holidayCalendarFinder: HolidayCalendarFinder,
    private val holidayCalendarRepository: HolidayCalendarRepository,
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

    fun add(year: Int, locale: Locale, request: HolidayCalendarResources.Request.Holiday) {
        val holidayCalendar = holidayCalendarFinder.findOrThrow(locale, year)

        val holiday = request.run {
            Holiday(date, name, type, description)
        }

        holidayCalendar.addBy(holiday)

        holidayCalendarRepository.save(holidayCalendar)
    }

    fun remove(locale: Locale, date: LocalDate) {
        val year = date.year
        val holidayCalendar = holidayCalendarFinder.findOrThrow(locale, year)

        holidayCalendar.removeBy(date)
        holidayCalendarRepository.save(holidayCalendar)
    }
}

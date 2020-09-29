package io.mustelidae.riverotter.domain.calendar.holiday

import io.mustelidae.riverotter.config.DataNotFoundException
import io.mustelidae.riverotter.domain.calendar.holiday.repository.HolidayCalendarRepository
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class HolidayCalendarFinder(
    private val holidayCalendarRepository: HolidayCalendarRepository
) {

    fun findOrThrow(year: Int, locale: Locale): HolidayCalendar {
        return holidayCalendarRepository.findByYearAndLocale(year, locale) ?: throw DataNotFoundException("$locale ($year) is not found")
    }
}

package io.mustelidae.riverotter.domain.calender.holiday

import io.mustelidae.riverotter.config.DataNotFoundException
import io.mustelidae.riverotter.domain.calender.holiday.repository.HolidayCalenderRepository
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class HolidayCalenderFinder(
    private val holidayCalenderRepository: HolidayCalenderRepository
) {

    fun findOrThrow(year: Int, locale: Locale): HolidayCalender {
        return holidayCalenderRepository.findByYearAndCountry(year, locale.country) ?: throw DataNotFoundException("$locale ($year) is not found")
    }
}

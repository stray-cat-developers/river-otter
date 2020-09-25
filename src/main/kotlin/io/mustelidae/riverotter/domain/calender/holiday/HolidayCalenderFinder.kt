package io.mustelidae.riverotter.domain.calender.holiday

import io.mustelidae.riverotter.config.DataNotFoundException
import io.mustelidae.riverotter.domain.calender.holiday.repository.HolidayCalenderRepository
import org.springframework.stereotype.Service

@Service
class HolidayCalenderFinder(
    private val holidayCalenderRepository: HolidayCalenderRepository
) {

    fun findOrThrow(year: Int, country: String): HolidayCalender {
        return holidayCalenderRepository.findByYearAndCountry(year, country) ?: throw DataNotFoundException("$country ($year) is not found")
    }
}

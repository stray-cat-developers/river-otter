package io.mustelidae.riverotter.domain.calendar.holiday

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.config.NotSupportCountryException
import io.mustelidae.riverotter.domain.calendar.holiday.country.KoreaHoliday
import io.mustelidae.riverotter.domain.calendar.holiday.country.UnitedStateHoliday
import io.mustelidae.riverotter.domain.calendar.holiday.repository.HolidayCalendarRepository
import io.mustelidae.riverotter.domain.client.ClientHandler
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class HolidayCrawler(
    clientHandler: ClientHandler,
    private val env: AppEnvironment,
    private val holidayCalendarRepository: HolidayCalendarRepository
) {
    private val governmentOpenClient = clientHandler.governmentOpenClient()
    private val worldHolidayClient = clientHandler.worldHolidayClient()

    fun crawling(year: Int, country: Locale): ObjectId {
        val countryHoliday = when (country) {
            Locale.KOREA -> KoreaHoliday(env.country.korea, governmentOpenClient, holidayCalendarRepository)
            Locale.US -> UnitedStateHoliday(worldHolidayClient, holidayCalendarRepository)
            else -> throw NotSupportCountryException(country)
        }

        return countryHoliday.create(year)
    }
}

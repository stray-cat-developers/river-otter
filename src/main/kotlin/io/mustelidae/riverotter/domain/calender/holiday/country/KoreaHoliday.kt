package io.mustelidae.riverotter.domain.calender.holiday.country

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.calender.holiday.Holiday
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import io.mustelidae.riverotter.domain.calender.holiday.repository.HolidayCalenderRepository
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenClient
import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class KoreaHoliday(
    korEnv: AppEnvironment.Country.Korea,
    private val governmentOpenClient: GovernmentOpenClient,
    private val holidayCalenderRepository: HolidayCalenderRepository,
) : CountryHoliday {

    override val localeOfCountry: Locale = Locale.KOREA
    private val patternOfLocDate = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val saturdayIsHoliday = korEnv.saturdayIsHoliday

    override fun create(year: Int): ObjectId {
        val holidayCalender = holidayCalenderRepository.findByYearAndLocale(year, localeOfCountry)
        if (holidayCalender != null)
            return holidayCalender.id

        val weekendHolidays = super.getWeekend(year, saturdayIsHoliday)

        val publicHolidays = governmentOpenClient.findAllHoliday(2020)
            .response.body.items.item
            .map {
                val date = LocalDate.parse(it.locdate.toString(), patternOfLocDate)
                Holiday(
                    date,
                    it.dateName,
                    Holiday.Type.PUBLIC_HOLIDAY
                )
            }

        val holidays = mutableListOf<Holiday>().apply {
            addAll(publicHolidays)
            addAll(weekendHolidays)
        }

        for (weekendHoliday in weekendHolidays) {
            publicHolidays.find { it.date == weekendHoliday.date }?.let {
                holidays.remove(weekendHoliday)
            }
        }

        holidays.sortBy { it.time }

        return holidayCalenderRepository.save(HolidayCalender(localeOfCountry, year, holidays)).id
    }
}

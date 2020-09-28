package io.mustelidae.riverotter.domain.calender.api

import com.fasterxml.jackson.annotation.JsonProperty
import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.domain.calender.holiday.Holiday
import io.mustelidae.riverotter.domain.calender.holiday.Holiday.Type
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

class HolidayCalenderResources {

    class Request {
        data class Crawling(
            val year: Int? = null,
            val country: String? = null
        ) {
            fun getYear(): Int = this.year ?: LocalDate.now().plusYears(1).year

            fun getLocale(): Locale? {
                return if (country.isNullOrEmpty().not())
                    AvailableCountry.getLocale(country!!)
                else
                    null
            }
        }
    }

    class Reply {

        data class YearOfCountry(
            val country: String,
            val id: String,
            val year: Int,
        )

        data class Calender(
            val id: String,
            val country: String,
            val year: Int,
            val days: List<Day>,
            val createdAt: LocalDateTime
        ) {
            companion object {
                fun from(holidayCalender: HolidayCalender): Calender {
                    val holidays = holidayCalender.holidays.map { Day.from(it, holidayCalender.locale) }
                    return Calender(
                        holidayCalender.id.toString(),
                        holidayCalender.locale.country,
                        holidayCalender.year,
                        holidays,
                        holidayCalender.createdAt
                    )
                }
            }
        }

        data class DayOfHoliday(
            @get:JsonProperty("isHoliday")
            val isHoliday: Boolean,
            val day: Day? = null
        ) {
            companion object {
                fun fromNotHoliday(): DayOfHoliday = DayOfHoliday(false, null)

                fun fromHasHoliday(holiday: Holiday, locale: Locale): DayOfHoliday {
                    return DayOfHoliday(
                        true,
                        Day.from(holiday, locale)
                    )
                }
            }
        }

        data class Day(
            val date: LocalDate,
            val month: Int,
            val day: Int,
            val name: String,
            val type: Type,
            val dayOfWeek: String,
            val description: String? = null
        ) {
            companion object {
                fun from(holiday: Holiday, locale: Locale): Day {
                    return holiday.run {
                        Day(
                            date,
                            date.monthValue,
                            date.dayOfMonth,
                            name,
                            type,
                            date.dayOfWeek.getDisplayName(TextStyle.FULL, locale),
                            description
                        )
                    }
                }
            }
        }
    }
}

package io.mustelidae.riverotter.domain.calendar.api

import com.fasterxml.jackson.annotation.JsonProperty
import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday.Type
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendar
import io.swagger.annotations.ApiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

class HolidayCalendarResources {

    class Request {
        @ApiModel("HolidayCalendar.Request.Crawling")
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

        @ApiModel("HolidayCalendar.Reply.YearOfCountry")
        data class YearOfCountry(
            val country: String,
            val id: String,
            val year: Int,
        )

        @ApiModel("HolidayCalendar.Reply.Calendar")
        data class Calendar(
            val id: String,
            val country: String,
            val year: Int,
            val days: List<Day>,
            val createdAt: LocalDateTime
        ) {
            companion object {
                fun from(holidayCalendar: HolidayCalendar): Calendar {
                    val holidays = holidayCalendar.holidays.map { Day.from(it, holidayCalendar.locale) }
                    return Calendar(
                        holidayCalendar.id.toString(),
                        holidayCalendar.locale.country,
                        holidayCalendar.year,
                        holidays,
                        holidayCalendar.createdAt
                    )
                }
            }
        }

        @ApiModel("HolidayCalendar.Reply.DayOfHoliday")
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

        @ApiModel("HolidayCalendar.Reply.Day")
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

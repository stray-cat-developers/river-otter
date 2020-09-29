package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.common.Replies
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.HolidayCalendarInteraction
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources.Reply.Calendar
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources.Reply.YearOfCountry
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources.Request
import io.mustelidae.riverotter.utils.toReplies
import io.mustelidae.riverotter.utils.toReply
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Holiday calendar"], description = "Holiday calendar")
@RestController
@RequestMapping("holiday/calendar")
class HolidayCalendarController(
    private val holidayCalendarInteraction: HolidayCalendarInteraction
) {

    @ApiOperation("Crawl holiday information.")
    @PostMapping("crawling")
    fun crawling(
        @RequestBody request: Request.Crawling
    ): Replies<YearOfCountry> {
        val yearsOfCountry = mutableListOf<YearOfCountry>()
        val year = request.getYear()

        if (request.getLocale() == null) {
            val pairs = holidayCalendarInteraction.crawling(year)
            yearsOfCountry.addAll(
                pairs.map {
                    YearOfCountry(it.first.country, it.second.toString(), year)
                }
            )
        } else {
            val pair = holidayCalendarInteraction.crawling(year, request.getLocale()!!)
            yearsOfCountry.add(
                YearOfCountry(pair.first.country, pair.second.toString(), year)
            )
        }

        return yearsOfCountry.toReplies()
    }

    @ApiOperation("Search the holiday list by year.")
    @GetMapping("country/{country}/year/{year}")
    fun findYear(
        @PathVariable country: String,
        @PathVariable year: Int
    ): Reply<Calendar> {
        val locale = AvailableCountry.getLocale(country)
        val calendar = holidayCalendarInteraction.findBy(locale, year)

        return Calendar.from(calendar).toReply()
    }

    @ApiOperation("Search the holiday list using the year and month.")
    @GetMapping("country/{country}/year/{year}/month/{month}")
    fun findMonth(
        @PathVariable country: String,
        @PathVariable year: Int,
        @PathVariable month: Int
    ): Replies<HolidayCalendarResources.Reply.Day> {
        val locale = AvailableCountry.getLocale(country)
        return holidayCalendarInteraction.findBy(locale, year, month)
            .map { HolidayCalendarResources.Reply.Day.from(it, locale) }
            .toReplies()
    }

    @ApiOperation("Search if the entered date is a holiday.")
    @GetMapping("country/{country}/year/{year}/month/{month}/day/{day}")
    fun findDay(
        @PathVariable country: String,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): Reply<HolidayCalendarResources.Reply.DayOfHoliday> {
        val locale = AvailableCountry.getLocale(country)
        val holiday = holidayCalendarInteraction.findBy(locale, year, month, day)

        val dayOfHoliday = if (holiday == null)
            HolidayCalendarResources.Reply.DayOfHoliday.fromNotHoliday()
        else
            HolidayCalendarResources.Reply.DayOfHoliday.fromHasHoliday(holiday, locale)

        return dayOfHoliday.toReply()
    }

    @ApiOperation("Search if the entered date is a holiday.")
    @GetMapping("country/{country}")
    fun findDayWithParam(
        @PathVariable country: String,
        @RequestParam year: Int,
        @RequestParam month: Int,
        @RequestParam day: Int
    ): Reply<HolidayCalendarResources.Reply.DayOfHoliday> {
        return this.findDay(country, year, month, day)
    }
}

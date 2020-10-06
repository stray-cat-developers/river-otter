package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.common.Replies
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.api.HolidayCalendarResources
import io.mustelidae.riverotter.domain.topic.TopicHolidayCalendarFinder
import io.mustelidae.riverotter.utils.toReplies
import io.mustelidae.riverotter.utils.toReply
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Topic holiday calendar"], description = "Topic holiday calendar")
@RestController
@RequestMapping("topics/{id}/calendar/country/{country}")
class TopicHolidayCalendarController(
    private val topicHolidayCalendarFinder: TopicHolidayCalendarFinder
) {

    @ApiOperation("Search the topic holiday list by year.")
    @GetMapping("year/{year}")
    fun findYear(
        @PathVariable id: String,
        @PathVariable country: String,
        @PathVariable year: Int
    ): Reply<HolidayCalendarResources.Reply.Calendar> {
        val locale = AvailableCountry.getLocale(country)
        val calendar = topicHolidayCalendarFinder.findBy(ObjectId(id), locale, year)

        return HolidayCalendarResources.Reply.Calendar.from(calendar).toReply()
    }

    @ApiOperation("Search the topic holiday list using the year and month.")
    @GetMapping("year/{year}/month/{month}")
    fun findMonth(
        @PathVariable id: String,
        @PathVariable country: String,
        @PathVariable year: Int,
        @PathVariable month: Int
    ): Replies<HolidayCalendarResources.Reply.HolidayOfCountry> {
        val locale = AvailableCountry.getLocale(country)
        return topicHolidayCalendarFinder.findBy(ObjectId(id), locale, year, month)
            .map { HolidayCalendarResources.Reply.HolidayOfCountry.from(it, locale) }
            .toReplies()
    }

    @ApiOperation("Search if the entered date is a topic holiday.")
    @GetMapping("year/{year}/month/{month}/day/{day}")
    fun findDay(
        @PathVariable id: String,
        @PathVariable country: String,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): Reply<HolidayCalendarResources.Reply.DayOfHoliday> {
        val locale = AvailableCountry.getLocale(country)
        val holiday = topicHolidayCalendarFinder.findBy(ObjectId(id), locale, year, month, day)

        val dayOfHoliday = if (holiday == null)
            HolidayCalendarResources.Reply.DayOfHoliday.fromNotHoliday()
        else
            HolidayCalendarResources.Reply.DayOfHoliday.fromHasHoliday(holiday, locale)

        return dayOfHoliday.toReply()
    }

    @ApiOperation("Search if the entered date is a topic holiday.")
    @GetMapping()
    fun findDayWithParam(
        @PathVariable id: String,
        @PathVariable country: String,
        @RequestParam year: Int,
        @RequestParam month: Int,
        @RequestParam day: Int
    ): Reply<HolidayCalendarResources.Reply.DayOfHoliday> {
        return this.findDay(id, country, year, month, day)
    }
}

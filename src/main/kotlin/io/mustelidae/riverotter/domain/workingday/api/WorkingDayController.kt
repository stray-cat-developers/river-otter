package io.mustelidae.riverotter.domain.workingday.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendarFinder
import io.mustelidae.riverotter.domain.topic.TopicHolidayCalendarFinder
import io.mustelidae.riverotter.domain.workingday.WorkingDayCalculator
import io.mustelidae.riverotter.utils.toReply
import io.swagger.annotations.ApiOperation
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import io.swagger.annotations.Api

@Api(tags = ["Working day calculator"], description = "Calculate business days.")
@RestController
@RequestMapping("working-day")
class WorkingDayController(
    private val holidayCalendarFinder: HolidayCalendarFinder,
    private val topicHolidayCalendarFinder: TopicHolidayCalendarFinder
) {

    @ApiOperation("Calculate business days based on the national calendar.")
    @GetMapping("/calendar/holiday/country/{country}/working-day")
    fun calculateWorkingDayUsingCountryHoliday(
        @PathVariable country: String,
        @RequestParam date: LocalDate,
        @RequestParam(required = false) businessDays: Int? = 1
    ): Reply<LocalDate> {

        val locale = AvailableCountry.getLocale(country)
        val plusDate = date.plusDays((businessDays!! + 50).toLong())

        val holidays = holidayCalendarFinder.findOrThrow(locale, date.year).holidays.toMutableList()

        if (plusDate.year > date.year)
            holidays.addAll(holidayCalendarFinder.findOrThrow(locale, plusDate.year).holidays)

        val calculator = WorkingDayCalculator(holidays)

        return calculator.calculate(date, businessDays)
            .toReply()
    }

    @ApiOperation("Calculate business days based on topic calendar.")
    @GetMapping("topics/{id}/calendar/country/{country}/working-day")
    fun calculateWorkingDayUsingTopicHoliday(
        @PathVariable id: String,
        @PathVariable country: String,
        @RequestParam date: LocalDate,
        @RequestParam(required = false) businessDays: Int? = 1
    ): Reply<LocalDate> {
        val locale = AvailableCountry.getLocale(country)
        val plusDate = date.plusDays((businessDays!! + 50).toLong())

        val holidays = topicHolidayCalendarFinder.findBy(ObjectId(id), locale, date.year).holidays.toMutableList()

        if (plusDate.year > date.year)
            holidays.addAll(topicHolidayCalendarFinder.findBy(ObjectId(id), locale, plusDate.year).holidays)

        val calculator = WorkingDayCalculator(holidays)

        return calculator.calculate(date, businessDays)
            .toReply()
    }
}

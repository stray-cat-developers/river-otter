package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.HolidayCalendarInteraction
import io.mustelidae.riverotter.utils.toReply
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Tag(name = "Holiday calendar", description = "Holiday calendar")
@RestController
@RequestMapping("/v1/maintenance/calendar/holiday/country/{country}")
class HolidayCalendarMaintenanceController(
    private val holidayCalendarInteraction: HolidayCalendarInteraction,
) {

    @Operation(summary = "Add new holidays.")
    @PostMapping
    fun addHoliday(
        @RequestBody request: HolidayCalendarResources.Request.Holiday,
        @PathVariable country: String,
    ): Reply<Unit> {
        val locale = AvailableCountry.getLocale(country)
        val year = request.date.year

        holidayCalendarInteraction.add(year, locale, request)
        return Unit.toReply()
    }

    @Operation(summary = "Remove existing public holidays.")
    @DeleteMapping("year/{year}/month/{month}/day/{day}")
    fun removeHoliday(
        @PathVariable country: String,
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int,
    ): Reply<Unit> {
        val locale = AvailableCountry.getLocale(country)

        val date = LocalDate.of(year, month, day)

        holidayCalendarInteraction.remove(locale, date)
        return Unit.toReply()
    }
}

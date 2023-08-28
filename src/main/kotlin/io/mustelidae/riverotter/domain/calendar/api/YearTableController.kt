package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.yeartable.YearTableFinder
import io.mustelidae.riverotter.utils.toReply
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Holiday calendar", description = "Stored calendar by year")
@RestController
@RequestMapping("calendar/holiday")
class YearTableController(
    private val yearTableFinder: YearTableFinder,
) {

    @Operation(summary = "Years Table", description = "Stored calendar by year")
    @GetMapping("country/{country}/table")
    fun findYearsOfLocale(
        @PathVariable country: String,
    ): Reply<YearTableResources.Reply.Table> {
        val locale = AvailableCountry.getLocale(country)
        val yearTable = yearTableFinder.findOrThrow(locale)
        return YearTableResources.Reply.Table.from(yearTable).toReply()
    }
}

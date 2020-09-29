package io.mustelidae.riverotter.domain.calender.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calender.yeartable.YearTableFinder
import io.mustelidae.riverotter.utils.toReply
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Years Table"], description = "Stored calendar by year")
@RestController
@RequestMapping("holiday/calender")
class YearTableController(
    private val yearTableFinder: YearTableFinder
) {

    @GetMapping("country/{country}/table")
    fun findYearsOfLocale(
        @PathVariable country: String
    ): Reply<YearTableResources.Reply.Table> {
        val locale = AvailableCountry.getLocale(country)
        val yearTable = yearTableFinder.findOrThrow(locale)
        return YearTableResources.Reply.Table.from(yearTable).toReply()
    }
}

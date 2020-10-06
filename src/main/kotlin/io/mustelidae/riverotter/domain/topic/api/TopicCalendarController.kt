package io.mustelidae.riverotter.domain.topic.api

import io.mustelidae.riverotter.common.AvailableCountry
import io.mustelidae.riverotter.domain.topic.TopicInteraction
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Api(tags = ["Topic calendar"], description = "Topic custom calendar")
@RestController
@RequestMapping("topics/{id}")
class TopicCalendarController(
    private val topicInteraction: TopicInteraction
) {

    @ApiOperation("Add holidays to calendar.")
    @PostMapping("holidays/{country}")
    fun addHoliday(
        @PathVariable id: String,
        @PathVariable country: String,
        @RequestBody request: TopicCalendarResources.Request.TopicHoliday
    ) {
        val locale = AvailableCountry.getLocale(country)
        request.run {
            topicInteraction.addHoliday(ObjectId(id), locale, date, name, description)
        }
    }

    @ApiOperation("Remove holidays to calendar.")
    @DeleteMapping("holidays/{country}/dates/{date}")
    fun removeHoliday(
        @PathVariable id: String,
        @PathVariable country: String,
        @PathVariable date: LocalDate
    ) {
        val locale = AvailableCountry.getLocale(country)
        topicInteraction.removeHoliday(ObjectId(id), locale, date)
    }

    @ApiOperation("Add workdays to calendar.")
    @PostMapping("workdays/{country}")
    fun addWorkday(
        @PathVariable id: String,
        @PathVariable country: String,
        @RequestBody request: TopicCalendarResources.Request.TopicWorkday
    ) {
        val locale = AvailableCountry.getLocale(country)
        request.run {
            topicInteraction.addWorkday(ObjectId(id), locale, date, name, type, description)
        }
    }

    @ApiOperation("Remove workdays to calendar.")
    @DeleteMapping("workdays/{country}/dates/{date}")
    fun removeWorkday(
        @PathVariable id: String,
        @PathVariable country: String,
        @PathVariable date: LocalDate
    ) {
        val locale = AvailableCountry.getLocale(country)
        topicInteraction.removeWorkday(ObjectId(id), locale, date)
    }
}

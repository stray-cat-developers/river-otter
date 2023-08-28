package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.PlanetCalendar
import io.mustelidae.riverotter.utils.toReply
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Tag(name = "Convert planet date", description = "Solar date to Lunar date, Lunar date to Solar date")
@RestController
@RequestMapping("calendar")
class PlanetCalendarController {

    @GetMapping("solar-to-lunar")
    fun solarToLunar(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        date: LocalDate,
    ): Reply<LocalDate> {
        return PlanetCalendar().solarToLunar(date)
            .toReply()
    }

    @GetMapping("lunar-to-solar")
    fun lunarToSolar(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        date: LocalDate,
    ): Reply<LocalDate> {
        return PlanetCalendar().lunarToSolar(date)
            .toReply()
    }
}

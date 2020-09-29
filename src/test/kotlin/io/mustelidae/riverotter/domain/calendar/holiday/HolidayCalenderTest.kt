package io.mustelidae.riverotter.domain.calendar.holiday

import java.time.LocalDate
import java.util.Locale

internal class HolidayCalenderTest

internal fun HolidayCalendar.Companion.aFixture(year: Int): HolidayCalendar {
    return HolidayCalendar(
        Locale.KOREA,
        year,
        listOf(
            Holiday(
                LocalDate.of(year, 1, 5),
                "",
                Holiday.Type.WEEKEND_HOLIDAY
            )
        )
    )
}

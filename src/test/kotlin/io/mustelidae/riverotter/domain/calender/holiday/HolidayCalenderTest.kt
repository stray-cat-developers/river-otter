package io.mustelidae.riverotter.domain.calender.holiday

import java.time.LocalDate
import java.util.Locale

internal class HolidayCalenderTest

internal fun HolidayCalender.Companion.aFixture(year: Int): HolidayCalender {
    return HolidayCalender(
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

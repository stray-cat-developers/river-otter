package io.mustelidae.riverotter.domain.calender.holiday

import java.time.LocalDate

class Holiday(
    val date: LocalDate,
    val name: String,
    val type: Type,
    val description: String? = null
) {

    val month: Int = date.month.value
    val day: Int = date.dayOfMonth

    enum class Type {
        PUBLIC_HOLIDAY,
        WEEKEND_HOLIDAY
    }
}

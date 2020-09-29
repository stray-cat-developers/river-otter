package io.mustelidae.riverotter.domain.calendar.holiday

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.ZoneOffset

@Document
class Holiday(
    val date: LocalDate,
    val name: String,
    val type: Type,
    val description: String? = null
) {
    var time: Long = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        protected set

    enum class Type {
        PUBLIC_HOLIDAY,
        WEEKEND_HOLIDAY
    }
}

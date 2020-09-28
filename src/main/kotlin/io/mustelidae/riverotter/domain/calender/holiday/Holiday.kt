package io.mustelidae.riverotter.domain.calender.holiday

import java.time.LocalDate
import java.time.ZoneOffset
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Holiday(
    final val date: LocalDate,
    val name: String,
    val type: Type,
    val description: String? = null
) {
    val time: Long = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC)

    enum class Type {
        PUBLIC_HOLIDAY,
        WEEKEND_HOLIDAY
    }
}

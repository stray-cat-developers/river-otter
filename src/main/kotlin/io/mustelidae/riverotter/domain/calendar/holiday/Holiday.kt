package io.mustelidae.riverotter.domain.calendar.holiday

import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDate
import java.time.ZoneOffset

class Holiday(
    @Indexed
    val date: LocalDate,
    val name: String,
    val type: Type,
    val description: String? = null
) {
    var time: Long = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        private set

    enum class Type {
        PUBLIC_HOLIDAY,
        WEEKEND_HOLIDAY,
        TOPIC_HOLIDAY,
        SCHEDULE_HOLIDAY
    }
}

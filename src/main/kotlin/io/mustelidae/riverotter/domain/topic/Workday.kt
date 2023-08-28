package io.mustelidae.riverotter.domain.topic

import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDate
import java.time.ZoneOffset

class Workday(
    @Indexed
    val date: LocalDate,
    val name: String,
    val type: Type,
    val description: String? = null,
) {
    var time: Long = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        private set

    enum class Type {
        REPLACED_WORK,
        FORCE_WORK,
    }
}

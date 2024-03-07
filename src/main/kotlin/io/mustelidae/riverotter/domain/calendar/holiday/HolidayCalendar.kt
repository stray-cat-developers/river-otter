package io.mustelidae.riverotter.domain.calendar.holiday

import io.mustelidae.riverotter.common.Error
import io.mustelidae.riverotter.common.ErrorCode
import io.mustelidae.riverotter.config.HumanException
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

@Suppress("ProtectedInFinal")
@Document
class HolidayCalendar(
    val locale: Locale,
    val year: Int,
    var holidays: List<Holiday>,
) {
    @Id
    var id: ObjectId = ObjectId()
        protected set
    var createdAt = LocalDateTime.now()!!
        protected set

    fun addBy(holiday: Holiday) {
        if (hasHoliday(holiday.date)) {
            throw HumanException(Error(ErrorCode.HD03, "There is already a public holiday for that date."))
        }

        holidays += holiday
    }

    fun removeBy(localDate: LocalDate) {
        val holiday = this.holidays.find { it.date == localDate }

        holiday?.let {
            holidays -= holiday
        }
    }

    private fun hasHoliday(localDate: LocalDate): Boolean {
        val holiday = this.holidays.find { it.date == localDate }
        return (holiday != null)
    }

    companion object
}

package io.mustelidae.riverotter.domain.topic

import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

@Document
class TopicCalendar(
    val locale: Locale,
    val year: Int,
) {
    @Id
    var id: ObjectId = ObjectId()
        protected set

    var holidays: MutableList<Holiday> = mutableListOf()
        protected set
    var workdays: MutableList<Workday> = mutableListOf()
        protected set

    var createdAt = LocalDateTime.now()!!
        protected set

    var modifiedAt = LocalDateTime.now()!!
        protected set

    fun addBy(holiday: Holiday) {
        if (holiday.type != Holiday.Type.TOPIC_HOLIDAY) {
            throw IllegalStateException("Only TOPIC_HOLIDAY is allowed in the topic calendar.")
        }

        holidays.find { it.date == holiday.date }?.let {
            throw IllegalArgumentException("already exists working day")
        }

        holidays.add(holiday)
        modifiedAt = LocalDateTime.now()
    }

    fun addBy(workday: Workday) {
        workdays.find { it.date == workday.date }?.let {
            throw IllegalArgumentException("already exists working day")
        }

        workdays.add(workday)
        modifiedAt = LocalDateTime.now()
    }

    fun removeHoliday(date: LocalDate) {
        holidays.removeIf { it.date == date }
        modifiedAt = LocalDateTime.now()
    }

    fun removeWorkDay(date: LocalDate) {
        workdays.removeIf { it.date == date }
        modifiedAt = LocalDateTime.now()
    }

    companion object
}

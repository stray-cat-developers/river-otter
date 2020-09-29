package io.mustelidae.riverotter.domain.calendar.holiday

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.Locale

@Document
class HolidayCalendar(
    val locale: Locale,
    val year: Int,
    val holidays: List<Holiday>
) {
    @Id
    var id: ObjectId = ObjectId()
        protected set
    var createdAt = LocalDateTime.now()!!
        protected set

    companion object
}

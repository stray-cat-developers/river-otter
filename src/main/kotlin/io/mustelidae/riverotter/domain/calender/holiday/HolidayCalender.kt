package io.mustelidae.riverotter.domain.calender.holiday

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.Locale

@Document
class HolidayCalender(
    locale: Locale,
    val year: Int,
    val holidays: List<Holiday>
) {
    val country = locale.country!!

    @Id
    var id: ObjectId = ObjectId()
        private set
    var createdAt = LocalDateTime.now()!!
        private set

    companion object
}

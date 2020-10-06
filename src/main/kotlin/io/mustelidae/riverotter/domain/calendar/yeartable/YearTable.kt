package io.mustelidae.riverotter.domain.calendar.yeartable

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.Locale

@Document
class YearTable(
    val locale: Locale,
    val yearsOfLocale: MutableList<YearOfLocale>
) {

    @Id
    var id: ObjectId = ObjectId()
        protected set

    var modifiedAt: LocalDateTime = LocalDateTime.now()
        protected set

    fun addBy(year: Int, id: ObjectId) {
        val calendar = yearsOfLocale.find { it.year == year }

        if (calendar != null)
            yearsOfLocale.remove(calendar)

        yearsOfLocale.add(YearOfLocale(year, id))
        modifiedAt = LocalDateTime.now()
    }

    fun removeBy(year: Int) {
        val calendar = yearsOfLocale.find { it.year == year }
        if (calendar != null)
            yearsOfLocale.remove(calendar)
        modifiedAt = LocalDateTime.now()
    }

    data class YearOfLocale(
        val year: Int,
        var id: ObjectId
    )

    companion object
}

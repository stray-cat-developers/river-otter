package io.mustelidae.riverotter.domain.calender.yeartable

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
        val calender = yearsOfLocale.find { it.year == year }
        if (calender == null)
            yearsOfLocale.add(YearOfLocale(year, id))
        else {
            val index = yearsOfLocale.indexOf(calender)
            yearsOfLocale[index].id = id
        }
        modifiedAt = LocalDateTime.now()
    }

    fun removeBy(year: Int) {
        val calender = yearsOfLocale.find { it.year == year }
        if (calender != null)
            yearsOfLocale.remove(calender)
        modifiedAt = LocalDateTime.now()
    }

    data class YearOfLocale(
        val year: Int,
        var id: ObjectId
    )

    companion object
}

package io.mustelidae.riverotter.domain.calendar.yeartable

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import java.util.Locale

internal class YearTableTest {

    @Test
    fun addBy() {
        // Given
        val yearTable = YearTable.aFixture()
        val year = 2021
        val id = ObjectId()
        // When
        yearTable.addBy(year, id)
        // Then
        yearTable.yearsOfLocale.size shouldBe 2
    }

    @Test
    fun updateId() {
        // Given
        val yearTable = YearTable.aFixture()
        val beforeId = yearTable.yearsOfLocale.first().id
        val year = 2020
        val id = ObjectId()
        // When
        yearTable.addBy(year, id)
        // Then
        val updatedYear = yearTable.yearsOfLocale.find { it.year == year }!!
        updatedYear.id shouldNotBe beforeId
    }
}

internal fun YearTable.Companion.aFixture(locale: Locale = Locale.KOREA): YearTable {
    return YearTable(locale, mutableListOf(YearTable.YearOfLocale(2020, ObjectId())))
}

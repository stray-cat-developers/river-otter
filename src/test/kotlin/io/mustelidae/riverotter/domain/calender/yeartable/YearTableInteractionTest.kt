package io.mustelidae.riverotter.domain.calender.yeartable

import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mustelidae.riverotter.domain.calender.yeartable.repository.YearTableRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import java.util.Locale

internal class YearTableInteractionTest {

    private val yearTableRepository: YearTableRepository = mockk()
    private val yearTableFinder: YearTableFinder = mockk()

    private val yearTableInteraction = YearTableInteraction(yearTableRepository, yearTableFinder)

    @Test
    fun save() {
        // Given
        val locale = Locale.KOREA
        val year = 2020
        val calenderId = ObjectId()

        val slot = slot<YearTable>()
        every { yearTableFinder.findOrThrow(locale) } returns YearTable.aFixture()
        every { yearTableFinder.hasYearTable(locale) } returns true
        every { yearTableRepository.save(capture(slot)) } answers { YearTable.aFixture() }

        // When

        yearTableInteraction.save(locale, year, calenderId)

        // Then
        val savedYearTable = slot.captured
        val yearOfLocale = savedYearTable.yearsOfLocale.find { it.year == year }!!

        yearOfLocale.id shouldBe calenderId
    }

    @Test
    fun saveNotExistYearTable() {
        // Given
        val locale = Locale.KOREA
        val year = 2020
        val calenderId = ObjectId()

        val slot = slot<YearTable>()
        every { yearTableFinder.hasYearTable(locale) } returns false
        every { yearTableRepository.save(capture(slot)) } answers { YearTable.aFixture() }

        // When

        yearTableInteraction.save(locale, year, calenderId)

        // Then
        val savedYearTable = slot.captured
        val yearOfLocale = savedYearTable.yearsOfLocale.find { it.year == year }!!

        yearOfLocale.id shouldBe calenderId
    }
}

package io.mustelidae.riverotter.domain.calender.holiday.country

import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import io.mustelidae.riverotter.domain.calender.holiday.aFixture
import io.mustelidae.riverotter.domain.calender.holiday.repository.HolidayCalenderRepository
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayDummyClient
import org.junit.jupiter.api.Test

internal class UnitedStateHolidayTest {

    private val holidayCalenderRepository: HolidayCalenderRepository = mockk()
    private val worldHolidayClient = WorldHolidayDummyClient()

    private val usaHoliday = UnitedStateHoliday(worldHolidayClient, holidayCalenderRepository)

    @Test
    fun create() {
        // Given
        val year = 2020

        val slot = slot<HolidayCalender>()
        every { holidayCalenderRepository.findByYearAndLocale(year, any()) } returns null
        every { holidayCalenderRepository.save(capture(slot)) } answers { HolidayCalender.Companion.aFixture(year) }

        // When
        usaHoliday.create(year)

        // Then
        val savedCalender = slot.captured
        savedCalender.locale shouldBe usaHoliday.localeOfCountry
        savedCalender.holidays.size shouldBe 61
    }
}

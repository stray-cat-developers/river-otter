package io.mustelidae.riverotter.domain.calendar.holiday.country

import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendar
import io.mustelidae.riverotter.domain.calendar.holiday.aFixture
import io.mustelidae.riverotter.domain.calendar.holiday.repository.HolidayCalendarRepository
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayDummyClient
import org.junit.jupiter.api.Test

internal class UnitedStateHolidayTest {

    private val holidayCalenderRepository: HolidayCalendarRepository = mockk()
    private val worldHolidayClient = WorldHolidayDummyClient()

    private val usaHoliday = UnitedStateHoliday(worldHolidayClient, holidayCalenderRepository)

    @Test
    fun create() {
        // Given
        val year = 2020

        val slot = slot<HolidayCalendar>()
        every { holidayCalenderRepository.findByYearAndLocale(year, any()) } returns null
        every { holidayCalenderRepository.save(capture(slot)) } answers { HolidayCalendar.Companion.aFixture(year) }

        // When
        usaHoliday.create(year)

        // Then
        val savedCalender = slot.captured
        savedCalender.locale shouldBe usaHoliday.localeOfCountry
        savedCalender.holidays.size shouldBe 61
    }
}

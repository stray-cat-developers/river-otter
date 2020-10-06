package io.mustelidae.riverotter.domain.calendar

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class PlanetCalendarTest {

    @Test
    fun solarToLunar1() {
        // Given
        val planetCalendar = PlanetCalendar()
        val solarDate = LocalDate.of(2017, 6, 24)

        // When
        val lunarDate = planetCalendar.solarToLunar(solarDate)

        // Then
        lunarDate shouldBe LocalDate.of(2017, 5, 1)
    }

    @Test
    fun solarToLunar2() {
        // Given
        val planetCalendar = PlanetCalendar()
        val solarDate = LocalDate.of(2020, 1, 1)

        // When
        val lunarDate = planetCalendar.solarToLunar(solarDate)

        // Then
        lunarDate shouldBe LocalDate.of(2019, 12, 7)
    }

    @Test
    fun solarToLunar3() {
        // Given
        val planetCalendar = PlanetCalendar()
        val solarDate = LocalDate.of(2020, 6, 6)

        // When
        val lunarDate = planetCalendar.solarToLunar(solarDate)

        // Then
        lunarDate shouldBe LocalDate.of(2020, 4, 15)
    }

    @Test
    fun lunarToSolar1() {
        // Given
        val planetCalendar = PlanetCalendar()
        val lunarDate = LocalDate.of(2017, 5, 1)

        // When
        val solarDate = planetCalendar.lunarToSolar(lunarDate)

        // Then
        solarDate shouldBe LocalDate.of(2017, 6, 24)
    }

    @Test
    fun lunarToSolar2() {
        // Given
        val planetCalendar = PlanetCalendar()
        val lunarDate = LocalDate.of(2019, 12, 7)

        // When
        val solarDate = planetCalendar.lunarToSolar(lunarDate)

        // Then
        solarDate shouldBe LocalDate.of(2020, 1, 1)
    }

    @Test
    fun lunarToSolar3() {
        // Given
        val planetCalendar = PlanetCalendar()
        val lunarDate = LocalDate.of(2020, 4, 15)

        // When
        val solarDate = planetCalendar.lunarToSolar(lunarDate)

        // Then
        solarDate shouldBe LocalDate.of(2020, 6, 6)
    }
}

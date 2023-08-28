package io.mustelidae.riverotter.domain.workingday

import io.kotest.matchers.shouldBe
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class WorkingDayCalculatorTest {

    private val holidays = listOf(
        Holiday(LocalDate.of(2020, 1, 4), "", Holiday.Type.PUBLIC_HOLIDAY),
        Holiday(LocalDate.of(2020, 1, 1), "", Holiday.Type.PUBLIC_HOLIDAY),
        Holiday(LocalDate.of(2020, 1, 7), "", Holiday.Type.PUBLIC_HOLIDAY),
        Holiday(LocalDate.of(2020, 1, 5), "", Holiday.Type.PUBLIC_HOLIDAY),

    )

    @Test
    fun calculate1() {
        // Given
        val date = LocalDate.of(2019, 12, 31)
        val workingDay = 1
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2019, 12, 31)
    }

    @Test
    fun calculate2() {
        // Given
        val date = LocalDate.of(2019, 12, 31)
        val workingDay = 2
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 2)
    }

    @Test
    fun calculate3() {
        // Given
        val date = LocalDate.of(2019, 12, 31)
        val workingDay = 3
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 3)
    }

    @Test
    fun calculate4() {
        // Given
        val date = LocalDate.of(2020, 1, 1)
        val workingDay = 1
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 2)
    }

    @Test
    fun calculate5() {
        // Given
        val date = LocalDate.of(2020, 1, 1)
        val workingDay = 2
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 3)
    }

    @Test
    fun calculate6() {
        // Given
        val date = LocalDate.of(2020, 1, 1)
        val workingDay = 3
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 6)
    }

    @Test
    fun calculate7() {
        // Given
        val date = LocalDate.of(2020, 1, 1)
        val workingDay = 4
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 8)
    }

    @Test
    fun calculate8() {
        // Given
        val date = LocalDate.of(2019, 12, 31)
        val workingDay = 4
        val calculator = WorkingDayCalculator(holidays.toMutableList())

        // When
        val businessDate = calculator.calculate(date, workingDay)

        // Then
        businessDate shouldBe LocalDate.of(2020, 1, 6)
    }
}

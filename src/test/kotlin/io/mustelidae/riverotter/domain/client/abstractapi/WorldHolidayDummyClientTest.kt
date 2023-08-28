package io.mustelidae.riverotter.domain.client.abstractapi

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Locale

internal class WorldHolidayDummyClientTest {

    private val worldHolidayClient = WorldHolidayDummyClient()

    @Test
    fun findHoliday() {
        // Given
        val country = Locale.US
        val year = 2020
        val month = 12
        val day = 25

        // When
        val holidays = worldHolidayClient.findHoliday(country, year, month, day)

        // Then
        holidays.size shouldBe 1
    }
}

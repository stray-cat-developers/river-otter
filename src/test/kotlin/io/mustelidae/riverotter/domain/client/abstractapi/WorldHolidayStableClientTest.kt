package io.mustelidae.riverotter.domain.client.abstractapi

import io.kotest.matchers.shouldBe
import io.mustelidae.riverotter.config.AppEnvironment
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.Locale

@Disabled
internal class WorldHolidayStableClientTest {

    private val env = AppEnvironment.Client.Abstract().apply {
        host = "https://holidays.abstractapi.com"
        key = "b7814953c3474896aae378fada8cd409"
        timeout = 5000
    }

    private val worldHolidayClient = WorldHolidayStableClient(env)

    @Test
    fun findHoliday() {
        // Given
        val year = 2020
        val month = 12
        val day = 25
        val country = Locale.US

        // When
        val holidays = worldHolidayClient.findHoliday(country, year, month, day)

        // Then
        holidays.size shouldBe 1
        holidays.first().type shouldBe "National"
    }
}

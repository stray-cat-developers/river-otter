package io.mustelidae.riverotter.domain.client.korea.government

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class GovernmentOpenDummyClientTest {

    @Test
    fun findAllHoliday() {
        // Given
        val client = GovernmentOpenDummyClient()

        // when
        val holiday = client.findAllHoliday(2020)

        // then
        holiday.response.body.items.item.size shouldBe 18
    }
}

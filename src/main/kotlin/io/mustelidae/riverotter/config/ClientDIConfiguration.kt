package io.mustelidae.riverotter.config

import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayClient
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayDummyClient
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayStableClient
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenClient
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenDummyClient
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenStableClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientDIConfiguration(
    val appEnv: AppEnvironment,
) {

    @Bean
    fun governmentOpenClient(): GovernmentOpenClient {
        return if (appEnv.client.government.useDummy) {
            GovernmentOpenDummyClient()
        } else {
            GovernmentOpenStableClient(appEnv.client.government)
        }
    }

    @Bean
    fun worldHolidayClient(): WorldHolidayClient {
        return if (appEnv.client.abstract.useDummy) {
            WorldHolidayDummyClient()
        } else {
            WorldHolidayStableClient(appEnv.client.abstract)
        }
    }
}

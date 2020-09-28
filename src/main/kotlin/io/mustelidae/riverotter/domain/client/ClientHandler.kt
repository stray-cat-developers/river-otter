package io.mustelidae.riverotter.domain.client

import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayClient
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayDummyClient
import io.mustelidae.riverotter.domain.client.abstractapi.WorldHolidayStableClient
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenClient
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenDummyClient
import io.mustelidae.riverotter.domain.client.korea.government.GovernmentOpenStableClient
import org.springframework.stereotype.Component

@Component
class ClientHandler(
    val appEnv: AppEnvironment
) {

    fun governmentOpenClient(): GovernmentOpenClient {
        return if (appEnv.client.government.useDummy)
            GovernmentOpenDummyClient()
        else
            GovernmentOpenStableClient(appEnv.client.government)
    }

    fun worldHolidayClient(): WorldHolidayClient {
        return if (appEnv.client.abstract.useDummy)
            WorldHolidayDummyClient()
        else
            WorldHolidayStableClient(appEnv.client.abstract)
    }
}

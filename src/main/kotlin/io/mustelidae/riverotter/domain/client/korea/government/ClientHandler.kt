package io.mustelidae.riverotter.domain.client.korea.government

import io.mustelidae.riverotter.config.AppEnvironment
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
}

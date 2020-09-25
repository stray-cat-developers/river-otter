package io.mustelidae.riverotter.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
class AppEnvironment {
    var client = Client()

    var saturdayIsHoliday: Boolean = true

    class Client {
        var government = Government()
        var abstract = Abstract()

        class Government {
            var useDummy: Boolean = false
            lateinit var host: String
            lateinit var key: String
            var timeout: Int = 3000
        }

        class Abstract {
            var useDummy: Boolean = false
            lateinit var host: String
            lateinit var key: String
            var timeout: Int = 3000
        }
    }
}

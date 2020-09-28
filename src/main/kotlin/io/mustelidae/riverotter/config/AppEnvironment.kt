package io.mustelidae.riverotter.config

import io.mustelidae.riverotter.common.AvailableCountry
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.Locale

@Configuration
@ConfigurationProperties(prefix = "app")
class AppEnvironment {
    var client = Client()
    var country = Country()

    private lateinit var availableCountries: List<String>

    fun getAvailableLocales(): List<Locale> {
        return availableCountries.map { AvailableCountry.getLocale(it) }
    }

    class Country {
        var korea = Korea()
        class Korea {
            var saturdayIsHoliday: Boolean = true
        }
    }

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

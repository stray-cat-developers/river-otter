package io.mustelidae.riverotter.common

import java.util.Locale

/**
 * Currently available countries
 */
object AvailableCountry {

    fun getLocale(country: String): Locale {
        return when (country) {
            "KR" -> Locale.KOREA
            "US" -> Locale.US
            else -> throw IllegalArgumentException("$country not mapping locale.")
        }
    }
}

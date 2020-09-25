package io.mustelidae.riverotter.domain.client.abstractapi

import com.github.kittinunf.fuel.Fuel
import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.config.WorldHolidayClientException
import io.mustelidae.riverotter.utils.ClientSupport
import io.mustelidae.riverotter.utils.Jackson
import org.slf4j.LoggerFactory
import java.util.Locale

class WorldHolidayStableClient(
    private val env: AppEnvironment.Client.Abstract
) : ClientSupport(
    Jackson.getMapper(),
    false,
    LoggerFactory.getLogger(WorldHolidayStableClient::class.java)
),
    WorldHolidayClient {

    override fun findHoliday(country: Locale, year: Int, month: Int, day: Int): WorldHolidayResources.Reply.Holidays {
        val url = "${env.host}/v1/"
        val params = listOf(
            Pair("api_key", env.key),
            Pair("country", country.country),
            Pair("year", year),
            Pair("day", day),
            Pair("month", month)
        )
        return Fuel.get(url, params)
            .timeout(env.timeout)
            .responseString()
            .orElseThrow { WorldHolidayClientException(it!!) }
            .fromJson()
    }
}

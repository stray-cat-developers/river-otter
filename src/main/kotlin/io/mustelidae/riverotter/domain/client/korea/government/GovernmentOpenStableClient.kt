package io.mustelidae.riverotter.domain.client.korea.government

import com.github.kittinunf.fuel.Fuel
import io.mustelidae.riverotter.config.AppEnvironment
import io.mustelidae.riverotter.config.GovernmentOpenClientException
import io.mustelidae.riverotter.utils.ClientSupport
import io.mustelidae.riverotter.utils.Jackson
import org.slf4j.LoggerFactory

class GovernmentOpenStableClient(
    private val env: AppEnvironment.Client.Government
) : GovernmentOpenClient, ClientSupport(
    Jackson.getMapper(),
    true,
    LoggerFactory.getLogger(GovernmentOpenStableClient::class.java)
) {

    override fun findAllHoliday(year: Int): GovernmentOpenResources.Reply.Holiday {
        val url = "${env.host}/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=${env.key}"
        val params = listOf(
            Pair("_type", "json"),
            Pair("solYear", year),
            Pair("numOfRows", 50)
        )

        log.info(params.toJson())

        return Fuel.get(url, params)
            .timeout(env.timeout)
            .responseString()
            .orElseThrow { GovernmentOpenClientException(it!!) }
            .fromJson()
    }
}

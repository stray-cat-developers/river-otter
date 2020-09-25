package io.mustelidae.riverotter.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.kittinunf.result.Result
import org.slf4j.Logger
import org.springframework.http.HttpStatus

open class ClientSupport(
    val objectMapper: ObjectMapper,
    private val writeLog: Boolean,
    protected val log: Logger
) {

    fun <T> T.toJson(): String = objectMapper.writeValueAsString(this)

    fun ResponseResultOf<String>.orElseThrow(throwException: (fuelError: FuelError?) -> Exception): Result<String, FuelError> {
        writeLog(this)
        val (_, res, result) = this

        if (res.isOk().not()) {
            throw throwException(result.component2())
        }

        return result
    }

    /**
     * writeLog 상태에 따라 평소에 Request Response 로그를 남길지 말지 결정한다.
     */
    private fun writeLog(response: ResponseResultOf<String>) {
        val (req, res, result) = response

        if (writeLog && res.isOk())
            log.info("$req\n-----------\n$res")

        if (res.isOk().not()) {
            val msg = StringBuilder("$req\n-----------\n$res")
            result.component2()?.let {
                msg.append("\n-- error --\n${String(it.response.data)}")
            }
            log.error(msg.toString())
        }
    }

    private fun Response.isOk(): Boolean {
        if (this.statusCode == -1)
            return false

        return (HttpStatus.valueOf(this.statusCode).is2xxSuccessful)
    }

    inline fun <reified T> String.fromJson(): T = objectMapper.readValue(this)

    inline fun <reified T> Result<String, FuelError>.fromJson(): T {
        return this.component1()!!
            .fromJson()
    }
}

package io.mustelidae.riverotter.config

import com.github.kittinunf.fuel.core.FuelError
import io.mustelidae.riverotter.common.Error
import io.mustelidae.riverotter.common.ErrorCode
import java.util.Locale

open class HumanException(val error: Error) : RuntimeException(error.getMessage())

class DataNotFoundException(cause: String) : HumanException(Error(ErrorCode.H000, cause))

class NotSupportCountryException(locale: Locale) : HumanException(Error(ErrorCode.H000, "not support country holiday calendar", causeBy = mapOf("locale" to locale.country)))

open class CommunicationException(val error: Error) : java.lang.RuntimeException(error.getMessage())

class GovernmentOpenClientException(fuelError: FuelError) : CommunicationException(
    Error(
        ErrorCode.C100,
        "Korea Public data API call failure",
        causeBy = mapOf(
            "response" to String(fuelError.response.data)
        )
    )
)

class WorldHolidayClientException(fuelError: FuelError) : CommunicationException(
    Error(
        ErrorCode.C100,
        "Abstractapi API call failure",
        causeBy = mapOf(
            "response" to String(fuelError.response.data)
        )
    )
)

open class SystemException(val error: Error) : RuntimeException(error.getMessage())

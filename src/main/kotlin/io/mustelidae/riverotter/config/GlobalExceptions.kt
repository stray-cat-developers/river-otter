package io.mustelidae.riverotter.config

import com.github.kittinunf.fuel.core.FuelError
import io.mustelidae.riverotter.common.Error
import io.mustelidae.riverotter.common.ErrorCode
import java.util.Locale

open class HumanException(val error: Error) : RuntimeException(error.getMessage())

class DataNotFoundException(cause: String) : HumanException(Error(ErrorCode.H000, cause))

class NotSupportCountryException(locale: Locale) : HumanException(Error(ErrorCode.H000, "not support country holiday calendar", causeBy = mapOf("locale" to locale.country)))

open class CommunicationException(error: Error) : java.lang.RuntimeException(error.getMessage())

class GovernmentOpenClientException(fuelError: FuelError) : CommunicationException(Error(ErrorCode.C100))

class WorldHolidayClientException(fuelError: FuelError) : CommunicationException(Error(ErrorCode.C100))

open class SystemException(error: Error) : RuntimeException(error.getMessage())

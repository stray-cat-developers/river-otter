package io.mustelidae.riverotter.config

import io.mustelidae.riverotter.common.Error
import io.mustelidae.riverotter.common.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import javax.servlet.http.HttpServletRequest

@Suppress("unused")
@ControllerAdvice(annotations = [RestController::class])
class RestAPIExceptionAdvice {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [RuntimeException::class, IllegalStateException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGlobalException(e: RuntimeException, request: HttpServletRequest): Map<String, Any> {
        return errorForm(request, Error(ErrorCode.S000, e.message))
    }

    @ExceptionHandler(value = [HumanException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleHumanException(e: HumanException, request: HttpServletRequest): Map<String, Any> {
        return errorForm(request, e.error)
    }

    @ExceptionHandler(value = [CommunicationException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleCommunicationException(e: CommunicationException, request: HttpServletRequest): Map<String, Any> {
        return errorForm(request, e.error)
    }

    @ExceptionHandler(value = [SystemException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleCommunicationException(e: SystemException, request: HttpServletRequest): Map<String, Any> {
        return errorForm(request, e.error)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalArgumentException(e: IllegalArgumentException, request: HttpServletRequest): Map<String, Any> {
        return errorForm(request, Error(ErrorCode.HI00, e.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): MutableMap<String, Any> {
        val error = errorForm(request, Error(ErrorCode.HI00))

        error["errors"]?.let {
            @Suppress("UNCHECKED_CAST")
            error["errors"] = methodArgumentNotValidExceptionErrorForm(it as List<FieldError>)
        }
        return error
    }

    /**
     * Default error format
     */
    private fun errorForm(request: HttpServletRequest, error: Error): MutableMap<String, Any> {

        val errorAttributes =
            DefaultErrorAttributes().getErrorAttributes(ServletWebRequest(request), ErrorAttributeOptions.defaults())

        errorAttributes["status"] = "false"
        errorAttributes["code"] = error.getCode()
        errorAttributes["message"] = error.getMessage()

        log.error("[${error.getCode()}] ${error.getMessage()} => ${error.getCause()}")

        return errorAttributes
    }

    @Suppress(
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    private fun methodArgumentNotValidExceptionErrorForm(errors: List<FieldError>) =
        errors.map {
            ValidationError(
                field = it.field,
                rejectedValue = it.rejectedValue.toString(),
                message = it.defaultMessage
            )
        }.toList()

    private data class ValidationError(
        val field: String,
        val rejectedValue: String,
        val message: String?
    )
}

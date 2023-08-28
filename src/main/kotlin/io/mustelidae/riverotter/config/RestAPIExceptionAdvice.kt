package io.mustelidae.riverotter.config

import io.mustelidae.riverotter.common.Error
import io.mustelidae.riverotter.common.ErrorCode
import io.mustelidae.riverotter.utils.Jackson
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest

@Suppress("unused")
@ControllerAdvice(annotations = [RestController::class])
class RestAPIExceptionAdvice(
    private val env: Environment,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(value = [RuntimeException::class, IllegalStateException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGlobalException(e: RuntimeException, request: HttpServletRequest): GlobalErrorFormat {
        return errorForm(request, e, Error(ErrorCode.S000, e.message))
    }

    @ExceptionHandler(value = [HumanException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleHumanException(e: HumanException, request: HttpServletRequest): GlobalErrorFormat {
        return errorForm(request, e, e.error)
    }

    @ExceptionHandler(value = [CommunicationException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleCommunicationException(e: CommunicationException, request: HttpServletRequest): GlobalErrorFormat {
        return errorForm(request, e, e.error)
    }

    @ExceptionHandler(value = [SystemException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleCommunicationException(e: SystemException, request: HttpServletRequest): GlobalErrorFormat {
        return errorForm(request, e, e.error)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalArgumentException(e: IllegalArgumentException, request: HttpServletRequest): GlobalErrorFormat {
        return errorForm(request, e, Error(ErrorCode.HI00, e.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): GlobalErrorFormat {
        return errorForm(
            request,
            e,
            Error(
                ErrorCode.HI00,
                e.bindingResult.fieldError?.defaultMessage ?: run {
                    ErrorCode.HI00.desc
                },
            ),
        )
    }

    /**
     * Default error format
     */
    private fun errorForm(request: HttpServletRequest, e: Exception, error: Error): GlobalErrorFormat {
        log.error("classification|error|${e.javaClass.simpleName}|${error.getCode()}", e)
        val errorAttributeOptions = if (env.activeProfiles.contains("prod").not()) {
            ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE)
        } else {
            ErrorAttributeOptions.defaults()
        }

        val errorAttributes =
            DefaultErrorAttributes().getErrorAttributes(ServletWebRequest(request), errorAttributeOptions)

        errorAttributes.apply {
            this["message"] = error.getMessage()
            this["code"] = error.getCode()
            this["causeBy"] = error.getCause()
            this["type"] = e.javaClass.simpleName
        }

        return Jackson.getMapper().convertValue(errorAttributes, GlobalErrorFormat::class.java)
    }

    private fun methodArgumentNotValidExceptionErrorForm(errors: List<FieldError>) =
        errors.map {
            ValidationError(
                field = it.field,
                rejectedValue = it.rejectedValue.toString(),
                message = it.defaultMessage,
            )
        }.toList()

    private data class ValidationError(
        val field: String,
        val rejectedValue: String,
        val message: String?,
    )
}

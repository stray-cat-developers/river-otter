package io.mustelidae.riverotter.common

class Error(
    private val code: ErrorCode,
    private val message: String? = null,
    private val causeBy: Map<String, Any?>? = null
) {
    fun getCode(): String = code.name
    fun getMessage(): String? {
        var msg = code.desc
        message?.let {
            msg += " cause by $it"
        }
        return message
    }

    fun getCause(): Map<String, Any?>? {
        return causeBy
    }
}

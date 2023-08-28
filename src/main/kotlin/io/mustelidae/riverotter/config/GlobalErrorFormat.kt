package io.mustelidae.riverotter.config

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "default error format")
data class GlobalErrorFormat(
    val timestamp: String,
    @Schema(description = "Http Status Code")
    val status: Int,
    @Schema(description = "error code")
    val code: String,
    @Schema(description = "exception message")
    val message: String,
)

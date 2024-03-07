package io.mustelidae.riverotter.config

import org.springdoc.core.models.GroupedOpenApi
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Configuration
class SwaggerConfiguration {

    init {
        SpringDocUtils.getConfig().replaceWithSchema(
            LocalDateTime::class.java,
            io.swagger.v3.oas.models.media.Schema<LocalDateTime>().apply {
                example(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            },
        )
        SpringDocUtils.getConfig().replaceWithSchema(
            LocalTime::class.java,
            io.swagger.v3.oas.models.media.Schema<LocalTime>().apply {
                example(LocalTime.now().format(DateTimeFormatter.ISO_TIME))
            },
        )
        SpringDocUtils.getConfig().replaceWithSchema(
            LocalDate::class.java,
            io.swagger.v3.oas.models.media.Schema<LocalDate>().apply {
                example(LocalDate.now().format(DateTimeFormatter.ISO_DATE))
            },
        )
    }

    @Bean
    fun default(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("API")
        .addOpenApiCustomizer {
            it.info.version("v1")
        }
        .packagesToScan("io.mustelidae.riverotter.domain")
        .pathsToExclude(
            "/v1/maintenance/**",
        )
        .build()

    @Bean
    fun maintenance(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("Maintenance")
        .addOpenApiCustomizer {
            it.info.version("v1")
        }
        .packagesToScan("io.mustelidae.riverotter.domain")
        .pathsToMatch(
            "/v1/maintenance/**",
        )
        .build()
}

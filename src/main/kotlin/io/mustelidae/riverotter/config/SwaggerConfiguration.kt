package io.mustelidae.riverotter.config

import com.fasterxml.classmate.TypeResolver
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

@EnableSwagger2
@Configuration
class SwaggerConfiguration {

    @Autowired
    private lateinit var typeResolver: TypeResolver

    @Bean
    fun docket(): Docket = Docket(DocumentationType.SWAGGER_2)
        .directModelSubstitute(LocalDate::class.java, String::class.java)
        .directModelSubstitute(LocalDateTime::class.java, String::class.java)
        .directModelSubstitute(LocalTime::class.java, String::class.java)
        .directModelSubstitute(Locale::class.java, String::class.java)
        .groupName("River otter")
        .select()
        .apis(RequestHandlerSelectors.basePackage("io.mustelidae.riverotter.domain"))
        .build()
        .additionalModels(typeResolver.resolve(GlobalErrorFormat::class.java))
}

@ApiModel(description = "Common error format")
data class GlobalErrorFormat(
    val timestamp: String,

    @ApiModelProperty(value = "Http Status Code")
    val status: Int,
    @ApiModelProperty(value = "error code")
    val code: String,
    @ApiModelProperty(value = "exception message")
    val message: String
)

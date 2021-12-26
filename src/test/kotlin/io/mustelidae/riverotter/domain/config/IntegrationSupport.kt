package io.mustelidae.riverotter.domain.config

import io.mustelidae.riverotter.RiverOtterApplication
import io.mustelidae.riverotter.config.SwaggerConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("embedded")
@DirtiesContext
@ComponentScan(excludeFilters = [ComponentScan.Filter(classes = [SwaggerConfiguration::class])])
@SpringBootTest(classes = [RiverOtterApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSupport {
    @LocalServerPort
    var port: Int = 0
}

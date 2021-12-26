import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    idea
    id("org.springframework.boot") version "2.5.8"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.avast.gradle.docker-compose") version "0.14.9"
    kotlin("jvm") version "1.5.32"
    kotlin("plugin.noarg") version "1.5.32"
    kotlin("plugin.spring") version "1.5.32"
    kotlin("plugin.allopen") version "1.5.32"
    id("org.jmailen.kotlinter") version "3.6.0"
    id("com.adarshr.test-logger") version "3.1.0"
}

group = "io.mustelidae"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenLocal()
    mavenCentral()
}

ext["log4j2.version"] = "2.17.0"

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")


    implementation("com.github.kittinunf.fuel:fuel:2.3.1")

    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.2.4")

    testImplementation("io.mockk:mockk:1.12.1")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-bean-validators:3.0.0")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")

    implementation("jakarta.persistence:jakarta.persistence-api:3.0.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-tomcat")
    }
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-undertow") {
        exclude("io.undertow", "undertow-websockets-jsr")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "junit", module = "junit")
    }
}

allOpen {
    annotation("javax.persistence.MappedSuperclass")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

noArg {
    annotation("javax.persistence.MappedSuperclass")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.register("version") {
    println(version)
}

testlogger {
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.STANDARD
    showExceptions = true
    showStackTraces = true
    showFullStackTraces = false
    showCauses = true
    slowThreshold = 2000
    showSummary = true
    showSimpleNames = true
    showPassed = true
    showSkipped = false
    showFailed = true
    showStandardStreams = false
    showPassedStandardStreams = true
    showSkippedStandardStreams = false
    showFailedStandardStreams = true
    logLevel = LogLevel.LIFECYCLE
}

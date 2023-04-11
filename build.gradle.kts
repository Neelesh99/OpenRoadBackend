import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDate

val http4kVersion = "4.41.3.0"
val kotlinVersion = "1.8.20"
val junitVersion = "5.9.2"
val mockkVersion = "1.13.4"

plugins {
    kotlin("jvm") version "1.7.21"
    application
    id("com.google.cloud.tools.jib") version "2.7.1"
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

jib {
    var tag = "latest"
    from {
        image = "gcr.io/distroless/java:19"
    }
    to {
        image = "ghcr.io/NeeleshRav99/openRoadBackend:latest"
        tags = setOf(version, tag) as MutableSet<String>
    }
    container {
        jvmFlags = listOf(
            "-server",
        "-Duser.timezone=UTC",
        "-Xms128m",
        "-Xmx1g")
        creationTime = LocalDate.now().toString()
        format = com.google.cloud.tools.jib.api.buildplan.ImageFormat.OCI
    }
}

group = "com.openroad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

testSets {
    "acceptanceTest"()
    "externalTest"()
}

dependencies {
    implementation("org.http4k:http4k-client-okhttp:${http4kVersion}")
    implementation("org.http4k:http4k-contract:${http4kVersion}")
    implementation("org.http4k:http4k-core:${http4kVersion}")
    implementation("org.http4k:http4k-format-jackson:${http4kVersion}")
    implementation("org.http4k:http4k-opentelemetry:${http4kVersion}")
    implementation("org.http4k:http4k-security-oauth:${http4kVersion}")
    implementation("org.http4k:http4k-server-undertow:${http4kVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
    implementation("io.arrow-kt:arrow-core:1.2.0-RC")
    implementation("io.klogging:klogging-jvm:0.4.13")
    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("org.http4k:http4k-testing-approval:${http4kVersion}")
    testImplementation("org.http4k:http4k-testing-hamkrest:${http4kVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}
plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
// Using extra property
project.extra["http4kVersion"] = "4.32.3.0"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.34.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.http4k:http4k-core:${rootProject.extra["http4kVersion"]}")
    implementation ("org.http4k:http4k-server-jetty:${rootProject.extra["http4kVersion"]}")
    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.http4k:http4k-client-jetty:${rootProject.extra["http4kVersion"]}")

    // Other dependencies...
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks.register("printProperties") {
    doLast {
        println(project.properties)
    }
}

tasks.register("printProperties4k") {
    doLast {
        println("http4kVersion: " + project.findProperty("http4kVersion"))
    }
}
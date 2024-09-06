plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "functional_programming"

// In settings.gradle.kts
settings.extra["http4kVersion"] = "4.32.3.0" // Replace with your desired version
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.com.diffplug.spotless)
}

spotless {
    kotlin {
        ktfmt("0.52").kotlinlangStyle()
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
    }
}

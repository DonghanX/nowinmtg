package com.donghanx.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension) {
    with(commonExtension) {
        compileSdk = 36
        defaultConfig.apply { minSdk = 26 }

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }

    configureKotlin()
}

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.set(
                freeCompilerArgs.get() +
                    listOf(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                        "-opt-in=kotlinx.coroutines.FlowPreview",
                    )
            )
        }
    }
}

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension) {
    commonExtension.apply {
        buildFeatures.apply { compose = true }

        configComposeCompiler()

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }
}

private fun Project.configComposeCompiler() {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        val destination = layout.buildDirectory.dir("compose_compiler")
        reportsDestination.set(destination)
        metricsDestination.set(destination)

        stabilityConfigurationFiles.add(
            rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
        )
    }
}

plugins {
    id("donghanx.jvm.library")
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
}

dependencies { implementation(libs.kotlinx.serialization) }

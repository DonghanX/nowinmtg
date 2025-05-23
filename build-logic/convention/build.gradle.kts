plugins { `kotlin-dsl` }

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions { jvmTarget = JavaVersion.VERSION_18.toString() }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "donghanx.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidApplication") {
            id = "donghanx.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "donghanx.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("jvmLibrary") {
            id = "donghanx.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "donghanx.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "donghanx.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidFeature") {
            id = "donghanx.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}

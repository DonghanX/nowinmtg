plugins {
    id("donghanx.android.library")
    id("donghanx.android.hilt")
}

android { namespace = "com.donghanx.domain" }

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.android)
}

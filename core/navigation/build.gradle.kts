plugins {
    id("donghanx.android.library")
    id("donghanx.android.library.compose")
    id("donghanx.android.hilt")
}

android { namespace = "com.donghanx.navigation" }

dependencies {
    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.navigation3.ui)
    api(libs.androidx.lifecycle.viewmodel.navigation3)

    testImplementation(libs.junit)
    testImplementation(libs.kotest)
    androidTestImplementation(libs.androidx.junit)
}

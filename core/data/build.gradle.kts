plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.nibalk.tasky.core.data"
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)

    // Retrofit/ Okhttp for networking
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.okhttp.loggiing)
    // Kotlinx Json for Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.retrofit)
    // Koin for DI
    implementation(libs.bundles.koin)
    // Timber for logging
    implementation(libs.timber)
}

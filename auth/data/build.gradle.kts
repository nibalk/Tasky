plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.nibalk.tasky.auth.data"
}

dependencies {
    // Project Modules
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    // Retrofit/ Okhttp for networking
    implementation(libs.squareup.retrofit)
    // Kotlinx Json for Serialization
    implementation(libs.kotlinx.serialization.json)
    // Koin for DI
    implementation(libs.bundles.koin)
    // Timber for logging
    implementation(libs.timber)
}

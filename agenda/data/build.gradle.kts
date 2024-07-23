plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.android.room)
}

android {
    namespace = "com.nibalk.tasky.agenda.data"
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.agenda.domain)
    implementation(projects.test)

    // Retrofit/ Okhttp for networking
    implementation(libs.squareup.retrofit)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Kotlinx Json for Serialization
    implementation(libs.kotlinx.serialization.json)
    // Koin for DI
    implementation(libs.bundles.koin)
    // Timber for logging
    implementation(libs.timber)
}

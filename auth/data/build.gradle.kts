plugins {
    alias(libs.plugins.tasky.android.library)
}

android {
    namespace = "com.nibalk.tasky.auth.data"
}

dependencies {
    // Project Modules
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    // Koin for DI
    implementation(libs.bundles.koin)
    // Timber for logging
    implementation(libs.timber)
}

plugins {
    alias(libs.plugins.tasky.android.library.compose)
}

android {
    namespace = "com.nibalk.tasky.core.presentation"
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)

    // Core
    implementation(libs.androidx.core.ktx)
    // Timber for logging
    implementation(libs.timber)
    // For Async Images
    implementation(libs.coil)
}

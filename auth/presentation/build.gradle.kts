plugins {
    alias(libs.plugins.tasky.android.feature.ui)
}

android {
    namespace = "com.nibalk.tasky.auth.presentation"
}

dependencies {
    // Project Modules
    implementation(projects.auth.domain)
    implementation(projects.core.domain)

    // Koin for DI
    implementation(libs.bundles.koin.compose)
}

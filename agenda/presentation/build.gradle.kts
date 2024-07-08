plugins {
    alias(libs.plugins.tasky.android.feature.ui)
}

android {
    namespace = "com.nibalk.tasky.agenda.presentation"
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)
    implementation(projects.agenda.domain)

    // Koin for DI
    implementation(libs.bundles.koin.compose)
    // Timber for logging
    implementation(libs.timber)
}

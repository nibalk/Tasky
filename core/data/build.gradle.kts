plugins {
    alias(libs.plugins.tasky.android.library)
}

android {
    namespace = "com.nibalk.tasky.core.data"
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)
    // Timber for logging
    implementation(libs.timber)
}

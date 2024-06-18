plugins {
    alias(libs.plugins.tasky.android.library)
}

android {
    namespace = "com.nibalk.tasky.agenda.data"
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)
    implementation(projects.agenda.domain)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Kotlinx Json for Serialization
    implementation(libs.kotlinx.serialization.json)
}

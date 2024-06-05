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
    // Serialization
    implementation(libs.kotlinx.serialization.json)
}

plugins {
    alias(libs.plugins.tasky.android.library.compose)
}

android {
    namespace = "com.nibalk.tasky.test"
}

dependencies {
    // Project Modules
    implementation(projects.agenda.domain)
}

plugins {
    alias(libs.plugins.tasky.jvm.library)
}

dependencies {
    // Project Modules
    implementation(projects.core.domain)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
}

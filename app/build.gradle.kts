plugins {
    alias(libs.plugins.tasky.android.application.compose)
}

android {
    namespace = "com.nibalk.tasky"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Project Modules - Core
    implementation(projects.core.presentation)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    // Project Modules - Auth
    implementation(projects.auth.presentation)
    implementation(projects.auth.domain)
    implementation(projects.auth.data)
    // Project Modules - Agenda
    implementation(projects.agenda.presentation)
    implementation(projects.agenda.domain)
    implementation(projects.agenda.data)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Crypto
    implementation(libs.androidx.security.crypto.ktx)
    // Koin for DI
    implementation(libs.bundles.koin.compose)
    // Kotlinx Json for Serialization
    implementation(libs.kotlinx.serialization.json)
    // Timber for Logging
    implementation(libs.timber)
}

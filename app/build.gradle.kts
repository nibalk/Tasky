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

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}

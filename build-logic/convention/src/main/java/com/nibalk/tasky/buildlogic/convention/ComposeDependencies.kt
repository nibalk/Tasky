package com.nibalk.tasky.buildlogic.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addComposeDependencies(project: Project) {
    project.run {
        val bom = libs.findLibrary("androidx.compose.bom").get()
        "implementation"(platform(bom))
        "androidTestImplementation"(platform(bom))

        "implementation"(libs.findBundle("compose").get())
        "debugImplementation"(libs.findBundle("compose.debug").get())
    }
}


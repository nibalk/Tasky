package com.nibalk.tasky.utils

import timber.log.Timber

class TaskyTimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "TaskyAppLogs"
    }
}

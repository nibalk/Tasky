package com.nibalk.tasky.auth.data

import android.util.Patterns
import com.nibalk.tasky.auth.domain.utils.AuthPatternValidator

object EmailAuthPatternValidator: AuthPatternValidator {

    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}

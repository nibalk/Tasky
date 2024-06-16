package com.nibalk.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nibalk.tasky.auth.presentation.register.RegisterScreenRoot
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskyTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { _ ->
                    // TODO: Replace with navigation controller
                    RegisterScreenRoot()
                }
            }
        }
    }
}

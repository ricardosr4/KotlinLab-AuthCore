package com.kotlinlab.authcore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kotlinlab.authcore.feature.auth.login.LoginScreen
import com.kotlinlab.authcore.ui.theme.KotlinLabAuthCoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinLabAuthCoreTheme {
                LoginScreen(
                    onNavigateToHome = {},
                    onNavigateToPasswordRecovery = {},
                    onNavigateToRegistration = {},
                )
            }
        }
    }
}

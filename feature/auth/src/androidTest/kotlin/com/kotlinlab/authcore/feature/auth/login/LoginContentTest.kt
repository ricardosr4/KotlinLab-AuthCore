package com.kotlinlab.authcore.feature.auth.login

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun emptyForm_disablesSignIn_andEmitsFieldActions() {
        val actions = mutableListOf<LoginAction>()

        composeRule.setContent {
            LoginContent(
                uiState = LoginUiState(),
                onAction = actions::add,
            )
        }

        composeRule.onNodeWithText("Iniciar sesión").assertIsNotEnabled()
        composeRule.onNodeWithText("Correo electrónico").performTextInput("ricardo@email.com")
        composeRule.onNodeWithText("Contraseña").performTextInput("password")
        composeRule.onNodeWithText("¿Olvidaste tu contraseña?").performClick()

        assertEquals(
            listOf(
                LoginAction.EmailChanged("ricardo@email.com"),
                LoginAction.PasswordChanged("password"),
                LoginAction.ForgotPasswordClicked,
            ),
            actions,
        )
    }
}

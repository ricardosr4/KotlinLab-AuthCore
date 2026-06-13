package com.kotlinlab.authcore.feature.auth.login

sealed interface LoginAction {
    data class EmailChanged(val email: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object SignInClicked : LoginAction
    data object ForgotPasswordClicked : LoginAction
    data object CreateAccountClicked : LoginAction
}

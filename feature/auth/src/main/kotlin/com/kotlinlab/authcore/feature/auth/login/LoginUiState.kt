package com.kotlinlab.authcore.feature.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: LoginError? = null,
) {
    val canSignIn: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}

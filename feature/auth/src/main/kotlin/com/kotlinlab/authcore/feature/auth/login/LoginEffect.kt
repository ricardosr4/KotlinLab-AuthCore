package com.kotlinlab.authcore.feature.auth.login

sealed interface LoginEffect {
    data object NavigateToHome : LoginEffect
    data object NavigateToPasswordRecovery : LoginEffect
    data object NavigateToRegistration : LoginEffect
}

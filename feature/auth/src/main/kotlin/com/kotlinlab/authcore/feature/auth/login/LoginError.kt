package com.kotlinlab.authcore.feature.auth.login

sealed interface LoginError {
    data object InvalidCredentials : LoginError
    data object InvalidEmail : LoginError
    data object UserNotFound : LoginError
    data object Network : LoginError
    data object Unknown : LoginError
}

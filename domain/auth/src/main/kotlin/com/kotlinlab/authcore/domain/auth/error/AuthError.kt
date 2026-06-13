package com.kotlinlab.authcore.domain.auth.error

import com.kotlinlab.authcore.core.common.error.AppError

sealed interface AuthError : AppError {
    data object InvalidCredentials : AuthError
    data object EmailAlreadyInUse : AuthError
    data object InvalidEmail : AuthError
    data object WeakPassword : AuthError
    data object UserNotFound : AuthError
}

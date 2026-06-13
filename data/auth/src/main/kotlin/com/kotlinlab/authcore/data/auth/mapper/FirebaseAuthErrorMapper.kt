package com.kotlinlab.authcore.data.auth.mapper

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.kotlinlab.authcore.core.common.error.AppError
import com.kotlinlab.authcore.core.common.error.CommonError
import com.kotlinlab.authcore.domain.auth.error.AuthError
import javax.inject.Inject

internal class FirebaseAuthErrorMapper @Inject constructor() : AuthErrorMapper {

    override fun map(throwable: Throwable): AppError = when (throwable) {
        is FirebaseNetworkException -> CommonError.Network
        is FirebaseAuthInvalidUserException -> AuthError.UserNotFound
        is FirebaseAuthUserCollisionException -> AuthError.EmailAlreadyInUse
        is FirebaseAuthWeakPasswordException -> AuthError.WeakPassword
        is FirebaseAuthInvalidCredentialsException -> mapInvalidCredentials(throwable)
        else -> CommonError.Unknown
    }

    private fun mapInvalidCredentials(
        exception: FirebaseAuthInvalidCredentialsException,
    ): AppError = when (exception.errorCode) {
        ERROR_INVALID_EMAIL -> AuthError.InvalidEmail
        else -> AuthError.InvalidCredentials
    }

    private companion object {
        const val ERROR_INVALID_EMAIL = "ERROR_INVALID_EMAIL"
    }
}

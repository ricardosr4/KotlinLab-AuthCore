package com.kotlinlab.authcore.data.auth.mapper

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.kotlinlab.authcore.core.common.error.CommonError
import com.kotlinlab.authcore.domain.auth.error.AuthError
import org.junit.Assert.assertEquals
import org.junit.Test

class FirebaseAuthErrorMapperTest {

    private val mapper = FirebaseAuthErrorMapper()

    @Test
    fun `maps network exception`() {
        assertEquals(
            CommonError.Network,
            mapper.map(FirebaseNetworkException("Network unavailable")),
        )
    }

    @Test
    fun `maps invalid email exception`() {
        assertEquals(
            AuthError.InvalidEmail,
            mapper.map(
                FirebaseAuthInvalidCredentialsException(
                    "ERROR_INVALID_EMAIL",
                    "Invalid email",
                ),
            ),
        )
    }

    @Test
    fun `maps invalid credentials exception`() {
        assertEquals(
            AuthError.InvalidCredentials,
            mapper.map(
                FirebaseAuthInvalidCredentialsException(
                    "ERROR_WRONG_PASSWORD",
                    "Wrong password",
                ),
            ),
        )
    }

    @Test
    fun `maps missing user exception`() {
        assertEquals(
            AuthError.UserNotFound,
            mapper.map(
                FirebaseAuthInvalidUserException(
                    "ERROR_USER_NOT_FOUND",
                    "User not found",
                ),
            ),
        )
    }

    @Test
    fun `maps user collision exception`() {
        assertEquals(
            AuthError.EmailAlreadyInUse,
            mapper.map(
                FirebaseAuthUserCollisionException(
                    "ERROR_EMAIL_ALREADY_IN_USE",
                    "Email already in use",
                ),
            ),
        )
    }

    @Test
    fun `maps weak password exception`() {
        assertEquals(
            AuthError.WeakPassword,
            mapper.map(
                FirebaseAuthWeakPasswordException(
                    "ERROR_WEAK_PASSWORD",
                    "Weak password",
                    "Password must contain more characters",
                ),
            ),
        )
    }
}

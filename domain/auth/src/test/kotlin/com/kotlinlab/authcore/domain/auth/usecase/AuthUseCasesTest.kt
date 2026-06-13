package com.kotlinlab.authcore.domain.auth.usecase

import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.model.AuthUser
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthUseCasesTest {

    private val user = AuthUser(
        id = "user-id",
        email = "ricardo@example.com",
        displayName = "Ricardo",
    )
    private val repository = FakeAuthRepository(user)

    @Test
    fun `sign in delegates credentials to repository`() = runTest {
        val result = SignInUseCase(repository)("ricardo@example.com", "password")

        assertEquals(AppResult.Success(user), result)
        assertEquals("ricardo@example.com", repository.signInEmail)
        assertEquals("password", repository.signInPassword)
    }

    @Test
    fun `register delegates user information to repository`() = runTest {
        val result = RegisterUserUseCase(repository)(
            displayName = "Ricardo",
            email = "ricardo@example.com",
            password = "password",
        )

        assertEquals(AppResult.Success(user), result)
        assertEquals("Ricardo", repository.registerDisplayName)
        assertEquals("ricardo@example.com", repository.registerEmail)
        assertEquals("password", repository.registerPassword)
    }

    @Test
    fun `password reset delegates email to repository`() = runTest {
        val result = SendPasswordResetEmailUseCase(repository)("ricardo@example.com")

        assertEquals(AppResult.Success(Unit), result)
        assertEquals("ricardo@example.com", repository.passwordResetEmail)
    }
}

private class FakeAuthRepository(
    private val user: AuthUser,
) : AuthRepository {

    var signInEmail: String? = null
    var signInPassword: String? = null
    var registerDisplayName: String? = null
    var registerEmail: String? = null
    var registerPassword: String? = null
    var passwordResetEmail: String? = null

    override suspend fun signIn(email: String, password: String): AppResult<AuthUser> {
        signInEmail = email
        signInPassword = password
        return AppResult.Success(user)
    }

    override suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AppResult<AuthUser> {
        registerDisplayName = displayName
        registerEmail = email
        registerPassword = password
        return AppResult.Success(user)
    }

    override suspend fun sendPasswordResetEmail(email: String): AppResult<Unit> {
        passwordResetEmail = email
        return AppResult.Success(Unit)
    }
}

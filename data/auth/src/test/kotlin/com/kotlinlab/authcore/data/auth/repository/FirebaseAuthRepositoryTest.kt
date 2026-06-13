package com.kotlinlab.authcore.data.auth.repository

import com.kotlinlab.authcore.core.common.error.CommonError
import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.data.auth.datasource.AuthDataSource
import com.kotlinlab.authcore.data.auth.mapper.AuthErrorMapper
import com.kotlinlab.authcore.data.auth.model.AuthUserData
import com.kotlinlab.authcore.domain.auth.model.AuthUser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FirebaseAuthRepositoryTest {

    private val userData = AuthUserData(
        id = "user-id",
        email = "ricardo@example.com",
        displayName = "Ricardo",
        photoUrl = null,
    )
    private val expectedUser = AuthUser(
        id = "user-id",
        email = "ricardo@example.com",
        displayName = "Ricardo",
        photoUrl = null,
    )

    @Test
    fun `sign in maps data user to domain user`() = runTest {
        val dataSource = FakeAuthDataSource(userData)
        val repository = createRepository(dataSource)

        val result = repository.signIn("ricardo@example.com", "password")

        assertEquals(AppResult.Success(expectedUser), result)
        assertEquals("ricardo@example.com", dataSource.signInEmail)
        assertEquals("password", dataSource.signInPassword)
    }

    @Test
    fun `register maps data user and delegates user information`() = runTest {
        val dataSource = FakeAuthDataSource(userData)
        val repository = createRepository(dataSource)

        val result = repository.register("Ricardo", "ricardo@example.com", "password")

        assertEquals(AppResult.Success(expectedUser), result)
        assertEquals("Ricardo", dataSource.registerDisplayName)
        assertEquals("ricardo@example.com", dataSource.registerEmail)
        assertEquals("password", dataSource.registerPassword)
    }

    @Test
    fun `password reset delegates email`() = runTest {
        val dataSource = FakeAuthDataSource(userData)
        val repository = createRepository(dataSource)

        val result = repository.sendPasswordResetEmail("ricardo@example.com")

        assertEquals(AppResult.Success(Unit), result)
        assertEquals("ricardo@example.com", dataSource.passwordResetEmail)
    }

    @Test
    fun `repository maps datasource exceptions to app errors`() = runTest {
        val expectedException = IllegalStateException("Firebase failure")
        val dataSource = FakeAuthDataSource(userData, expectedException)
        val repository = createRepository(dataSource)

        val result = repository.signIn("ricardo@example.com", "password")

        assertEquals(AppResult.Failure(CommonError.Unknown), result)
    }

    @Test
    fun `repository does not convert coroutine cancellation into failure`() {
        val cancellation = CancellationException("Operation cancelled")
        val dataSource = FakeAuthDataSource(userData, cancellation)
        val repository = createRepository(dataSource)

        assertThrows(CancellationException::class.java) {
            kotlinx.coroutines.test.runTest {
                repository.signIn("ricardo@example.com", "password")
            }
        }
    }

    private fun createRepository(dataSource: AuthDataSource): FirebaseAuthRepository =
        FirebaseAuthRepository(
            authDataSource = dataSource,
            errorMapper = AuthErrorMapper { CommonError.Unknown },
        )
}

private class FakeAuthDataSource(
    private val user: AuthUserData,
    private val throwable: Throwable? = null,
) : AuthDataSource {

    var signInEmail: String? = null
    var signInPassword: String? = null
    var registerDisplayName: String? = null
    var registerEmail: String? = null
    var registerPassword: String? = null
    var passwordResetEmail: String? = null

    override suspend fun signIn(email: String, password: String): AuthUserData {
        signInEmail = email
        signInPassword = password
        return resultOrThrow()
    }

    override suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AuthUserData {
        registerDisplayName = displayName
        registerEmail = email
        registerPassword = password
        return resultOrThrow()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        passwordResetEmail = email
        throwable?.let { throw it }
    }

    private fun resultOrThrow(): AuthUserData {
        throwable?.let { throw it }
        return user
    }
}

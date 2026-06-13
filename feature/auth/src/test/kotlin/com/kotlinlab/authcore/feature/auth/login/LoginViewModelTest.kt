package com.kotlinlab.authcore.feature.auth.login

import com.kotlinlab.authcore.core.common.error.CommonError
import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.model.AuthUser
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository
import com.kotlinlab.authcore.domain.auth.usecase.SignInUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `email and password actions update state`() {
        val viewModel = createViewModel()

        viewModel.onAction(LoginAction.EmailChanged("ricardo@example.com"))
        viewModel.onAction(LoginAction.PasswordChanged("password"))

        assertEquals("ricardo@example.com", viewModel.uiState.value.email)
        assertEquals("password", viewModel.uiState.value.password)
        assertTrue(viewModel.uiState.value.canSignIn)
    }

    @Test
    fun `sign in is ignored when credentials are empty`() = runTest {
        val repository = FakeAuthRepository()
        val viewModel = createViewModel(repository)

        viewModel.onAction(LoginAction.SignInClicked)
        advanceUntilIdle()

        assertFalse(repository.signInCalled)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `successful sign in emits navigation effect`() = runTest {
        val viewModel = createViewModel()
        viewModel.enterValidCredentials()
        val effect = async { viewModel.effects.first() }

        viewModel.onAction(LoginAction.SignInClicked)
        advanceUntilIdle()

        assertEquals(LoginEffect.NavigateToHome, effect.await())
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `failed sign in updates presentation error`() = runTest {
        val repository = FakeAuthRepository(AppResult.Failure(CommonError.Network))
        val viewModel = createViewModel(repository)
        viewModel.enterValidCredentials()

        viewModel.onAction(LoginAction.SignInClicked)
        advanceUntilIdle()

        assertEquals(LoginError.Network, viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `create account action emits registration effect`() = runTest {
        val viewModel = createViewModel()
        val effect = async { viewModel.effects.first() }

        viewModel.onAction(LoginAction.CreateAccountClicked)
        advanceUntilIdle()

        assertEquals(LoginEffect.NavigateToRegistration, effect.await())
    }

    private fun createViewModel(
        repository: AuthRepository = FakeAuthRepository(),
    ): LoginViewModel = LoginViewModel(SignInUseCase(repository))

    private fun LoginViewModel.enterValidCredentials() {
        onAction(LoginAction.EmailChanged(" ricardo@example.com "))
        onAction(LoginAction.PasswordChanged("password"))
    }
}

private class FakeAuthRepository(
    private val signInResult: AppResult<AuthUser> = AppResult.Success(
        AuthUser(id = "user-id", email = "ricardo@example.com"),
    ),
) : AuthRepository {

    var signInCalled = false

    override suspend fun signIn(email: String, password: String): AppResult<AuthUser> {
        signInCalled = true
        return signInResult
    }

    override suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AppResult<AuthUser> = error("Not required for LoginViewModel tests")

    override suspend fun sendPasswordResetEmail(email: String): AppResult<Unit> =
        error("Not required for LoginViewModel tests")
}

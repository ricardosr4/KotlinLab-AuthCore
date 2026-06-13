package com.kotlinlab.authcore.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinlab.authcore.core.common.error.AppError
import com.kotlinlab.authcore.core.common.error.CommonError
import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.error.AuthError
import com.kotlinlab.authcore.domain.auth.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val effectsChannel = Channel<LoginEffect>(capacity = Channel.BUFFERED)
    val effects = effectsChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged -> updateEmail(action.email)
            is LoginAction.PasswordChanged -> updatePassword(action.password)
            LoginAction.SignInClicked -> signIn()
            LoginAction.ForgotPasswordClicked -> emitEffect(LoginEffect.NavigateToPasswordRecovery)
            LoginAction.CreateAccountClicked -> emitEffect(LoginEffect.NavigateToRegistration)
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update { state ->
            state.copy(email = email, error = null)
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update { state ->
            state.copy(password = password, error = null)
        }
    }

    private fun signIn() {
        val state = _uiState.value
        if (!state.canSignIn) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = signInUseCase(state.email.trim(), state.password)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    effectsChannel.send(LoginEffect.NavigateToHome)
                }

                is AppResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.error.toLoginError(),
                        )
                    }
                }
            }
        }
    }

    private fun emitEffect(effect: LoginEffect) {
        viewModelScope.launch {
            effectsChannel.send(effect)
        }
    }
}

private fun AppError.toLoginError(): LoginError = when (this) {
    AuthError.InvalidCredentials -> LoginError.InvalidCredentials
    AuthError.InvalidEmail -> LoginError.InvalidEmail
    AuthError.UserNotFound -> LoginError.UserNotFound
    CommonError.Network -> LoginError.Network
    else -> LoginError.Unknown
}

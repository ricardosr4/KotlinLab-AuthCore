package com.kotlinlab.authcore.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToPasswordRecovery: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> onNavigateToHome()
                LoginEffect.NavigateToPasswordRecovery -> onNavigateToPasswordRecovery()
                LoginEffect.NavigateToRegistration -> onNavigateToRegistration()
            }
        }
    }

    LoginContent(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

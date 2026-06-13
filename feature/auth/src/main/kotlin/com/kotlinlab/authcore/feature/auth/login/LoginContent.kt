package com.kotlinlab.authcore.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlinlab.authcore.core.designsystem.component.AuthCoreButton
import com.kotlinlab.authcore.core.designsystem.component.AuthCorePasswordField
import com.kotlinlab.authcore.core.designsystem.component.AuthCoreTextField
import com.kotlinlab.authcore.feature.auth.R
import com.kotlinlab.authcore.feature.auth.login.component.LoginErrorMessage
import com.kotlinlab.authcore.feature.auth.login.component.LoginHeader

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 480.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                LoginHeader(
                    title = stringResource(R.string.login_title),
                    subtitle = stringResource(R.string.login_subtitle),
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                AuthCoreTextField(
                    value = uiState.email,
                    onValueChange = { onAction(LoginAction.EmailChanged(it)) },
                    label = stringResource(R.string.login_email_label),
                    enabled = !uiState.isLoading,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                )

                AuthCorePasswordField(
                    value = uiState.password,
                    onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
                    label = stringResource(R.string.login_password_label),
                    showPasswordText = stringResource(R.string.login_show_password),
                    hidePasswordText = stringResource(R.string.login_hide_password),
                    enabled = !uiState.isLoading,
                )

                uiState.error?.let { error ->
                    LoginErrorMessage(error = error)
                }

                AuthCoreButton(
                    text = stringResource(R.string.login_sign_in),
                    onClick = { onAction(LoginAction.SignInClicked) },
                    enabled = uiState.canSignIn,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.padding(top = 8.dp),
                )

                TextButton(
                    onClick = { onAction(LoginAction.ForgotPasswordClicked) },
                    enabled = !uiState.isLoading,
                ) {
                    Text(text = stringResource(R.string.login_forgot_password))
                }

                TextButton(
                    onClick = { onAction(LoginAction.CreateAccountClicked) },
                    enabled = !uiState.isLoading,
                ) {
                    Text(text = stringResource(R.string.login_create_account))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginContentPreview() {
    MaterialTheme {
        LoginContent(
            uiState = LoginUiState(
                email = "ricardo@email.com",
                password = "password",
            ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginContentErrorPreview() {
    MaterialTheme {
        LoginContent(
            uiState = LoginUiState(error = LoginError.InvalidCredentials),
            onAction = {},
        )
    }
}

package com.kotlinlab.authcore.feature.auth.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlinlab.authcore.feature.auth.login.LoginError
import com.kotlinlab.authcore.feature.auth.R

@Composable
fun LoginErrorMessage(
    error: LoginError,
    modifier: Modifier = Modifier,
) {
    val messageResource = when (error) {
        LoginError.InvalidCredentials -> R.string.login_error_invalid_credentials
        LoginError.InvalidEmail -> R.string.login_error_invalid_email
        LoginError.UserNotFound -> R.string.login_error_user_not_found
        LoginError.Network -> R.string.login_error_network
        LoginError.Unknown -> R.string.login_error_unknown
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            text = androidx.compose.ui.res.stringResource(messageResource),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

package com.kotlinlab.authcore.domain.auth.usecase

import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.model.AuthUser
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): AppResult<AuthUser> =
        authRepository.signIn(email, password)
}

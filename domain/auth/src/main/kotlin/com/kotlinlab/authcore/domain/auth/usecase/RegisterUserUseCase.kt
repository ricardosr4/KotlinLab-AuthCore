package com.kotlinlab.authcore.domain.auth.usecase

import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.model.AuthUser
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository

class RegisterUserUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        displayName: String,
        email: String,
        password: String,
    ): AppResult<AuthUser> = authRepository.register(displayName, email, password)
}

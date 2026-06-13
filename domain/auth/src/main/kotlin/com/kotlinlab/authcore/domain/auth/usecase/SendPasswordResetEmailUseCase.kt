package com.kotlinlab.authcore.domain.auth.usecase

import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository

class SendPasswordResetEmailUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String): AppResult<Unit> =
        authRepository.sendPasswordResetEmail(email)
}

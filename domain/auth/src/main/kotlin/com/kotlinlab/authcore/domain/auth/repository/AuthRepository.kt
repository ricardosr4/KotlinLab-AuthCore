package com.kotlinlab.authcore.domain.auth.repository

import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.domain.auth.model.AuthUser

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AppResult<AuthUser>

    suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AppResult<AuthUser>

    suspend fun sendPasswordResetEmail(email: String): AppResult<Unit>
}

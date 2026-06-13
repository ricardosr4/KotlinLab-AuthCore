package com.kotlinlab.authcore.data.auth.datasource

import com.kotlinlab.authcore.data.auth.model.AuthUserData

internal interface AuthDataSource {
    suspend fun signIn(email: String, password: String): AuthUserData

    suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AuthUserData

    suspend fun sendPasswordResetEmail(email: String)
}

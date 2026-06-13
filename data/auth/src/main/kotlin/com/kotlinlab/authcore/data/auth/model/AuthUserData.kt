package com.kotlinlab.authcore.data.auth.model

internal data class AuthUserData(
    val id: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
)

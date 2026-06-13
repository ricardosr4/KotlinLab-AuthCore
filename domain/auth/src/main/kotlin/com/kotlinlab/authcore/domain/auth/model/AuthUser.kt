package com.kotlinlab.authcore.domain.auth.model

data class AuthUser(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val photoUrl: String? = null,
)

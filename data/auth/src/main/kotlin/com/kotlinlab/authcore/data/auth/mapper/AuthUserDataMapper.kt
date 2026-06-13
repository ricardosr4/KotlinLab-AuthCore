package com.kotlinlab.authcore.data.auth.mapper

import com.kotlinlab.authcore.data.auth.model.AuthUserData
import com.kotlinlab.authcore.domain.auth.model.AuthUser

internal fun AuthUserData.toDomain(): AuthUser = AuthUser(
    id = id,
    email = email,
    displayName = displayName,
    photoUrl = photoUrl,
)

package com.kotlinlab.authcore.data.auth.mapper

import com.google.firebase.auth.FirebaseUser
import com.kotlinlab.authcore.data.auth.model.AuthUserData

internal fun FirebaseUser.toAuthUserData(): AuthUserData = AuthUserData(
    id = uid,
    email = requireNotNull(email) { "Firebase user has no email" },
    displayName = displayName,
    photoUrl = photoUrl?.toString(),
)

package com.kotlinlab.authcore.data.auth.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.kotlinlab.authcore.data.auth.mapper.toAuthUserData
import com.kotlinlab.authcore.data.auth.model.AuthUserData
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirebaseAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthDataSource {

    override suspend fun signIn(email: String, password: String): AuthUserData {
        val user = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            ?: error("Firebase returned no user after sign in")

        return user.toAuthUserData()
    }

    override suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AuthUserData {
        val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
            ?: error("Firebase returned no user after registration")
        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()

        user.updateProfile(profile).await()

        return user.toAuthUserData()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }
}

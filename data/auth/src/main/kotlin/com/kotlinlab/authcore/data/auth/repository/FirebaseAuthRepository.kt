package com.kotlinlab.authcore.data.auth.repository

import com.kotlinlab.authcore.core.common.result.AppResult
import com.kotlinlab.authcore.data.auth.datasource.AuthDataSource
import com.kotlinlab.authcore.data.auth.mapper.AuthErrorMapper
import com.kotlinlab.authcore.data.auth.mapper.toDomain
import com.kotlinlab.authcore.domain.auth.model.AuthUser
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

internal class FirebaseAuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val errorMapper: AuthErrorMapper,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): AppResult<AuthUser> =
        runAuthOperation {
            authDataSource.signIn(email, password).toDomain()
        }

    override suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): AppResult<AuthUser> = runAuthOperation {
        authDataSource.register(displayName, email, password).toDomain()
    }

    override suspend fun sendPasswordResetEmail(email: String): AppResult<Unit> =
        runAuthOperation {
            authDataSource.sendPasswordResetEmail(email)
        }

    private suspend fun <T> runAuthOperation(operation: suspend () -> T): AppResult<T> =
        try {
            AppResult.Success(operation())
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            AppResult.Failure(errorMapper.map(exception))
        }
}

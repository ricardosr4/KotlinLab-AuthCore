package com.kotlinlab.authcore.data.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.kotlinlab.authcore.data.auth.datasource.AuthDataSource
import com.kotlinlab.authcore.data.auth.datasource.FirebaseAuthDataSource
import com.kotlinlab.authcore.data.auth.mapper.AuthErrorMapper
import com.kotlinlab.authcore.data.auth.mapper.FirebaseAuthErrorMapper
import com.kotlinlab.authcore.data.auth.repository.FirebaseAuthRepository
import com.kotlinlab.authcore.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthDataModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(implementation: FirebaseAuthDataSource): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindAuthErrorMapper(implementation: FirebaseAuthErrorMapper): AuthErrorMapper

    @Binds
    @Singleton
    abstract fun bindAuthRepository(implementation: FirebaseAuthRepository): AuthRepository

    companion object {

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }
}

package com.kotlinlab.authcore.feature.auth.di

import com.kotlinlab.authcore.domain.auth.repository.AuthRepository
import com.kotlinlab.authcore.domain.auth.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object AuthPresentationModule {

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase =
        SignInUseCase(authRepository)
}

package com.tfg.veilcompanionapp.di

import com.tfg.veilcompanionapp.data.api.ApiService
import com.tfg.veilcompanionapp.data.local.datastore.UserPreferences
import com.tfg.veilcompanionapp.data.repository.AuthRepository
import com.tfg.veilcompanionapp.data.repository.FriendRepository
import com.tfg.veilcompanionapp.data.repository.GameRepository
import com.tfg.veilcompanionapp.data.repository.PlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepository(apiService, userPreferences)
    }

    @Provides
    @Singleton
    fun providePlayerRepository(apiService: ApiService): PlayerRepository {
        return PlayerRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideFriendRepository(apiService: ApiService): FriendRepository {
        return FriendRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideGameRepository(apiService: ApiService): GameRepository {
        return GameRepository(apiService)
    }
}
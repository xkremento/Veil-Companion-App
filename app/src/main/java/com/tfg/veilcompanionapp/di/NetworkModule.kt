package com.tfg.veilcompanionapp.di

import com.tfg.veilcompanionapp.data.api.ApiService
import com.tfg.veilcompanionapp.data.api.RetrofitClient
import com.tfg.veilcompanionapp.data.api.interceptors.AuthInterceptor
import com.tfg.veilcompanionapp.data.local.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://34.229.86.10:8080/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(userPreferences: UserPreferences): AuthInterceptor {
        return AuthInterceptor(userPreferences)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(authInterceptor: AuthInterceptor): RetrofitClient {
        return RetrofitClient(authInterceptor)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofitClient: RetrofitClient): ApiService {
        return retrofitClient.getApiService(BASE_URL)
    }
}
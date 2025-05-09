package com.tfg.veilcompanionapp.data.api

import com.google.gson.GsonBuilder
import com.tfg.veilcompanionapp.data.api.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {

    fun getApiService(baseUrl: String): ApiService {
        // Configurar el cliente OkHttp con interceptores
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        // Configurar Gson para manejar nulos adecuadamente
        val gson = GsonBuilder()
            .setLenient()
            .create()

        // Crear el cliente Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // Crear y devolver la implementación de la API
        return retrofit.create(ApiService::class.java)
    }
}
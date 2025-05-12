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
        // Configure the OkHttp client with interceptors
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor).connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        // Configure Gson to handle nulls properly
        val gson = GsonBuilder().setLenient().create()

        // Create Retrofit client
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

        // Create and return the API implementation
        return retrofit.create(ApiService::class.java)
    }
}
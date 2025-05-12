package com.tfg.veilcompanionapp.data.api.interceptors

import com.tfg.veilcompanionapp.data.local.datastore.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // For routes that don't need authentication, don't add token
        val path = request.url.encodedPath
        if (path.contains("/api/auth/login") || path.contains("/api/auth/register")) {
            return chain.proceed(request)
        }

        // Get JWT token from DataStore
        val token = runBlocking {
            userPreferences.authToken.first()
        }

        // If there's no token, proceed without it
        if (token.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        // Add token to request header
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
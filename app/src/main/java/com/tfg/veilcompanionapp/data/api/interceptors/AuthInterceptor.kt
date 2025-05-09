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

        // Para las rutas que no necesitan autenticación, no añadir token
        val path = request.url.encodedPath
        if (path.contains("/api/auth/login") || path.contains("/api/auth/register")) {
            return chain.proceed(request)
        }

        // Obtenemos el token JWT de DataStore
        // Nota: Esto es un bloqueo en un contexto síncrono, pero es necesario para el interceptor
        val token = runBlocking {
            userPreferences.authToken.first()
        }

        // Si no hay token, procedemos sin él
        if (token.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        // Añadimos el token al header de la petición
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
package com.tfg.veilcompanionapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        private val JWT_TOKEN = stringPreferencesKey("jwt_token")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NICKNAME = stringPreferencesKey("user_nickname")
    }

    // Save JWT token
    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
        }
    }

    // Get JWT token
    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[JWT_TOKEN]
    }

    // Save user information
    suspend fun saveUserInfo(email: String, nickname: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
            preferences[USER_NICKNAME] = nickname
        }
    }

    // Get user information
    val userEmail: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_EMAIL]
    }

    val userNickname: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_NICKNAME]
    }

    // Clear all data (logout)
    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
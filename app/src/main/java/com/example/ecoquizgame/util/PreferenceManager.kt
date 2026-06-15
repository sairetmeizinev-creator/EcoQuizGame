package com.example.ecoquizgame.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "eco_quiz_preferences")

class PreferenceManager(private val context: Context) {
    private val languageKey = stringPreferencesKey("language")
    private val soundEnabledKey = booleanPreferencesKey("sound_enabled")
    private val currentUserIdKey = stringPreferencesKey("current_user_id")
    private val currentUsernameKey = stringPreferencesKey("current_username")

    val languageFlow: Flow<String> = context.dataStore.data.map { it[languageKey] ?: "en" }
    val soundEnabledFlow: Flow<Boolean> = context.dataStore.data.map { it[soundEnabledKey] ?: true }
    val currentUserIdFlow: Flow<Long> = context.dataStore.data.map { it[currentUserIdKey]?.toLongOrNull() ?: -1L }
    val currentUsernameFlow: Flow<String> = context.dataStore.data.map { it[currentUsernameKey] ?: "" }

    suspend fun setLanguage(languageCode: String) {
        context.dataStore.edit { it[languageKey] = languageCode }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[soundEnabledKey] = enabled }
    }

    suspend fun setCurrentUser(userId: Long, username: String) {
        context.dataStore.edit {
            it[currentUserIdKey] = userId.toString()
            it[currentUsernameKey] = username
        }
    }
}

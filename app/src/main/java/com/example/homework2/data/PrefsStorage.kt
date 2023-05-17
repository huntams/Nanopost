package com.example.homework2.data

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    companion object {
        private const val PREFS_NAME = "Prefs"
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val USERNAME_KEY = "USERNAME_KEY"
    }

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var token: String?
        get() = prefs.getString(TOKEN_KEY, null)
        set(value) {
            prefs.edit {
                putString(TOKEN_KEY, value)
            }
        }

    var username: String?
        get() = prefs.getString(USERNAME_KEY, null)
        set(value) {
            prefs.edit {
                putString(USERNAME_KEY, value)
            }
        }


}
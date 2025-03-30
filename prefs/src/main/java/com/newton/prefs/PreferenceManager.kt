package com.newton.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PrefsConstants.PREFS_NAME, Context.MODE_PRIVATE
    )

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit {
            putBoolean(key, value)
        }
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit {
            putString(key, value)
        }
    }

    fun clear() {
        sharedPreferences.edit {
            clear()
        }
    }
}
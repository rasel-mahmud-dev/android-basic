package com.example.rs_app

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

data class AuthUser(
    val email: String,
    val username: String,
    val avatar: String,
    val id: UUID = UUID.randomUUID()
)

object AuthPreferences {
    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_AUTH_USER = "key_auth_user"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthUser(context: Context, user: AuthUser) {
        val gson = Gson()
        val json = gson.toJson(user)
        val editor = getPreferences(context).edit()
        editor.putString(KEY_AUTH_USER, json)
        editor.apply()
    }

    fun getAuthUser(context: Context): AuthUser? {
        val gson = Gson()
        val json = getPreferences(context).getString(KEY_AUTH_USER, null)
        return if (json != null) {
            val type = object : TypeToken<AuthUser>() {}.type
            gson.fromJson(json, type)
        } else {
            null
        }
    }

    fun clearAuthUser(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_AUTH_USER)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).contains(KEY_AUTH_USER)
    }
}
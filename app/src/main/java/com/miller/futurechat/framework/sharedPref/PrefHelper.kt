package com.miller.futurechat.framework.sharedPref

class PrefHelper(
    private val prefApi: SharedPrefApi
) {

    fun readToken() = prefApi.get(KEY_AUTH_TOKEN, "")

    fun setToken(token: String) = prefApi.set(KEY_AUTH_TOKEN, token)

    companion object {
        const val KEY_AUTH_TOKEN = "AuthToken"
    }
}
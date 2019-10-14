package com.miller.core.usecases.model

sealed class AuthState {
    data class LoggedIn(
        val token: String
    ) : AuthState()

    object LoggedOut : AuthState()
}
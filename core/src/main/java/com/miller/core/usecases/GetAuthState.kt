package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.usecases.model.AuthState.LoggedIn
import com.miller.core.usecases.model.AuthState.LoggedOut

class GetAuthState(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() = authenticationRepository.getToken().map { token: String? ->
        token?.let {
            LoggedIn(it)
        } ?: run {
            LoggedOut
        }
    }
}
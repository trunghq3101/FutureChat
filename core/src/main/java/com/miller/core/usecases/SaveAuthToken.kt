package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository

class SaveAuthToken(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(token: String) = authenticationRepository.updateToken(token)
}
package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository

/**
 * Created by Miller on 22/10/2019
 */

class SignOut(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() = authenticationRepository.signOut()
}
package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.utils.UseCaseUtils

/**
 * Created by Miller on 21/10/2019
 */

class GetUserInfo(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() = UseCaseUtils.withAuthenticated(authenticationRepository) {
        authenticationRepository.getUserInfo(it)
    }
}
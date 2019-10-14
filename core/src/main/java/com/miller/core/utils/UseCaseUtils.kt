package com.miller.core.utils

import com.miller.core.data.repository.AuthenticationRepository
import io.reactivex.Single
import io.reactivex.SingleSource

object UseCaseUtils {

    fun <T> withAuthenticated(
        authenticationRepository: AuthenticationRepository,
        consumer: (token: String) -> SingleSource<T>
    ): Single<T> {
        return authenticationRepository.getToken().flatMap(consumer)
    }
}
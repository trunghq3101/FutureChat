package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.NotificationTokenRepository
import com.miller.core.utils.UseCaseUtils

class AddNotificationToken(
    private val authenticationRepository: AuthenticationRepository,
    private val notificationTokenRepository: NotificationTokenRepository
) {
    operator fun invoke(notiToken: String) =
        UseCaseUtils.withAuthenticated(authenticationRepository) {
            notificationTokenRepository.addNotificationToken(notiToken, it)
        }
}
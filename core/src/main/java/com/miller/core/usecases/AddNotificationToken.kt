package com.miller.core.usecases

import com.miller.core.data.repository.NotificationTokenRepository

class AddNotificationToken(
    private val notificationTokenRepository: NotificationTokenRepository
) {
    operator fun invoke(notiToken: String, authToken: String) =
        notificationTokenRepository.addNotificationToken(notiToken, authToken)
}
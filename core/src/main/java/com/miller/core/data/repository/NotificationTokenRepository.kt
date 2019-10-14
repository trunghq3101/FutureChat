package com.miller.core.data.repository

import com.miller.core.data.datasource.NotificationTokenDataSource

class NotificationTokenRepository(private val dataSource: NotificationTokenDataSource) {

    fun addNotificationToken(token: String, authToken: String) =
        dataSource.add(token, authToken)
}
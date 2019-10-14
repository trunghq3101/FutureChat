package com.miller.core.data.datasource

import com.miller.core.domain.model.NotificationToken
import io.reactivex.Single

interface NotificationTokenDataSource {
    fun add(token: String, authToken: String): Single<NotificationToken>
}
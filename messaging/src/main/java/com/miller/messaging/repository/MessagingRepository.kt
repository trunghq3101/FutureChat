package com.miller.messaging.repository

import io.reactivex.Single

interface MessagingRepository {
    fun sendMessage(): Single<String>
}
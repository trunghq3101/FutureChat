package com.miller.core.data.datasource

import io.reactivex.Completable

interface UserDataSource {
    fun addConversation(userId: String, convId: String): Completable
}
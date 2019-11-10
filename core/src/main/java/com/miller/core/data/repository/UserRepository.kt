package com.miller.core.data.repository

import com.miller.core.data.datasource.UserDataSource

class UserRepository(
    private val dataSource: UserDataSource
) {
    fun addConversation(userId: String, convId: String) = dataSource.addConversation(userId, convId)
}
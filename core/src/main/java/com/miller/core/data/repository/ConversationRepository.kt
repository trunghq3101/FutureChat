package com.miller.core.data.repository

import com.miller.core.data.datasource.ConversationDataSource

class ConversationRepository(private val dataSource: ConversationDataSource) {
    fun getConversations(authToken: String) = dataSource.readAll(authToken)
}
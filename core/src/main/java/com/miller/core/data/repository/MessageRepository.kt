package com.miller.core.data.repository

import com.miller.core.data.datasource.MessageDataSource
import com.miller.core.domain.model.Message

class MessageRepository(
    private val dataSource: MessageDataSource
) {
    fun getMessages(authToken: String, conversationId: String) =
        dataSource.readMessages(authToken, conversationId)

    fun getPagingMessages(conversationId: String, lastItem: Message?) =
        dataSource.readPagingMessages(conversationId, lastItem)
}
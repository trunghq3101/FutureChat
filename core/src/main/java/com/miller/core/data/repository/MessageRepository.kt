package com.miller.core.data.repository

import com.miller.core.data.datasource.MessageDataSource

class MessageRepository(
    private val dataSource: MessageDataSource
) {
    fun getMessages(authToken: String, conversationId: String) =
        dataSource.readMessages(authToken, conversationId)

    fun getPagingMessagesAfter(conversationId: String, lastMsgId: String?) =
        dataSource.readPagingMessagesAfter(conversationId, lastMsgId)

    fun getPagingMessagesBefore(convId: String, firstMsgId: String) =
        dataSource.readPagingMessagesBefore(convId, firstMsgId)
}
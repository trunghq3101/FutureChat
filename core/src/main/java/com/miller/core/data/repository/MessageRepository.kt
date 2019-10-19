package com.miller.core.data.repository

import com.miller.core.data.datasource.LocalMessageDataSource
import com.miller.core.data.datasource.RemoteMessageDataSource
import com.miller.core.domain.model.Message
import io.reactivex.Completable

class MessageRepository(
    private val remoteDataSource: RemoteMessageDataSource,
    private val localDataSource: LocalMessageDataSource
) {
    fun getMessages(authToken: String, conversationId: String) =
        remoteDataSource.readMessages(authToken, conversationId)

    fun getPagingMessagesAfter(conversationId: String, lastMsgId: Int?) =
        remoteDataSource.readPagingMessagesAfter(conversationId, lastMsgId)

    fun getPagingMessagesBefore(convId: String, firstMsgId: Int) =
        remoteDataSource.readPagingMessagesBefore(convId, firstMsgId)

    fun saveMessageLocal(message: Message) = localDataSource.createMessage(message)

    fun saveMessageRemote(message: Message): Completable = remoteDataSource.createMessage(message)

}
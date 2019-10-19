package com.miller.core.data.datasource

import com.miller.core.domain.model.Message
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteMessageDataSource : MessageDataSource {
    fun readPagingMessagesAfter(conversationId: String, lastMsgId: Int?): Single<List<Message>>
    fun readPagingMessagesBefore(convId: String, firstMsgId: Int): Single<List<Message>>
    fun createMessage(msg: Message): Completable
}
package com.miller.core.data.datasource

import com.miller.core.domain.model.Message
import io.reactivex.Single

interface MessageDataSource {
    fun readMessages(authToken: String, conversationId: String): Single<List<Message>>
    fun readPagingMessages(conversationId: String, lastItem: Message?): Single<List<Message>>
}
package com.miller.core.data.datasource

import com.miller.core.domain.model.Conversation
import io.reactivex.Single

interface ConversationDataSource {
    fun readAll(authToken: String): Single<List<Conversation>>
    fun readPaging(authToken: String, lastConvId: String?): Single<List<Conversation>>
}
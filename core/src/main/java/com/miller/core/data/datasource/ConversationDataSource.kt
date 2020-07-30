package com.miller.core.data.datasource

import com.miller.core.domain.model.Conversation
import io.reactivex.Single

interface ConversationDataSource {
    fun readPagedFollowing(authToken: String, lastConvId: String?): Single<List<Conversation>>
    fun readPagedNewest(lastConvId: String?): Single<List<Conversation>>
}
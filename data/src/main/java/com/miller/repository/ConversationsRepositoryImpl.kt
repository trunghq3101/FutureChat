package com.miller.repository

import com.miller.conversations.model.ConversationItem
import com.miller.conversations.repository.ConversationsRepository
import io.reactivex.Single

/**
 * Created by Miller on 20/09/2019
 */

class ConversationsRepositoryImpl : ConversationsRepository {

    override fun fetchConversations(): Single<List<ConversationItem>> {
        return Single.just(listOf(ConversationItem("", "", "")))
    }

}
package com.miller.conversations.repository

import com.miller.conversations.model.ConversationItem
import io.reactivex.Single

/**
 * Created by Miller on 20/09/2019
 */

interface ConversationsRepository {
    fun fetchConversations(): Single<List<ConversationItem>>
}
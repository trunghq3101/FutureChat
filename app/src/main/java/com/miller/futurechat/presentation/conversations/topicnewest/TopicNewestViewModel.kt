package com.miller.futurechat.presentation.conversations.topicnewest

import com.miller.core.domain.model.Conversation
import com.miller.futurechat.presentation.conversations.BaseConversationViewModel
import com.miller.paging.PagingDataLoader
import org.koin.core.inject

class TopicNewestViewModel : BaseConversationViewModel() {
    override val pagingDataLoader: PagingDataLoader<Conversation> by inject()
}
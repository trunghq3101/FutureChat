package com.miller.futurechat.presentation.conversations.topicnewest

import com.miller.futurechat.presentation.conversations.BaseConversationViewModel
import org.koin.core.inject

class TopicNewestViewModel : BaseConversationViewModel() {
    override val pagingDataLoader: TopicNewestPagingDataLoader by inject()

}
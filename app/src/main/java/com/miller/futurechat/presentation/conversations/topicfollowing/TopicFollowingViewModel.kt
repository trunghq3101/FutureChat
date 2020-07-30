package com.miller.futurechat.presentation.conversations.topicfollowing

import com.miller.futurechat.presentation.conversations.BaseConversationViewModel
import org.koin.core.inject

/**
 * Created by Miller on 24/10/2019
 */

class TopicFollowingViewModel : BaseConversationViewModel() {
    override val pagingDataLoader: TopicFollowingPagingDataLoader by inject()
}
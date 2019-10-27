package com.miller.futurechat.presentation.conversations

import com.miller.core.domain.model.Conversation
import com.miller.futurechat.presentation.base.PagingViewModel
import com.miller.paging.fetchPage

abstract class BaseConversationViewModel : PagingViewModel<Conversation>() {

    fun loadConversations() {
        pagingWrapper.value = pagingDataLoader.fetchPage()
    }
}
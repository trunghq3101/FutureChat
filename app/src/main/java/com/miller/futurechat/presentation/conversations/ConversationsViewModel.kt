package com.miller.futurechat.presentation.conversations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.core.domain.model.Conversation
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.widget.ConversationPagingDataLoader
import com.miller.futurechat.presentation.base.PagingViewModel
import com.miller.futurechat.presentation.model.ConversationItem
import com.miller.paging.fetchPage
import org.koin.core.inject

class ConversationsViewModel: PagingViewModel<Conversation>() {

    override val pagingDataLoader: ConversationPagingDataLoader by inject()
    override val useCases: UseCases by inject()

    /*
    Observables
     */
    private val _conversations = MutableLiveData<List<ConversationItem>>()
    val conversations: LiveData<List<ConversationItem>> = _conversations

    /*
    ===============
     */

    fun loadConversations() {
        pagingWrapper.value = pagingDataLoader.fetchPage()
    }

    fun navigateToProfile() {
        navigate(ConversationsFragmentDirections.actionConversationsToProfile())
    }
}
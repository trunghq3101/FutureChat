package com.miller.futurechat.presentation.conversations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.core.domain.model.Conversation
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.framework.widget.ConversationPagingDataLoader
import com.miller.futurechat.presentation.base.PagingViewModel
import com.miller.futurechat.presentation.model.ConversationItem
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.paging.fetchPage
import org.koin.core.inject

class ConversationsViewModel: PagingViewModel<Conversation>() {

    override val pagingDataLoader: ConversationPagingDataLoader by inject()
    override val useCases: UseCases by inject()

    /*
    Observables
     */

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _conversations = MutableLiveData<List<ConversationItem>>()
    val conversations: LiveData<List<ConversationItem>> = _conversations

    /*
    ===============
     */

    fun loadAuthState() {
        useCases.getAuthState()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    _authState.value = it
                },
                {
                    onLoadFail(it)
                    _authState.value = AuthState.LoggedOut
                }
            ).apply {
                addDisposable(this)
            }
    }

    fun loadConversations() {
        pagingWrapper.value = pagingDataLoader.fetchPage()
    }
}
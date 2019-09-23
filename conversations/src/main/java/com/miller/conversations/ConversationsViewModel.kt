package com.miller.conversations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.common.base.BaseViewModel
import com.miller.common.utils.SchedulersUtils
import com.miller.common.utils.SingleLiveEvent
import com.miller.conversations.model.ConversationItem
import com.miller.conversations.repository.ConversationsRepository

/**
 * Created by Miller on 20/09/2019
 */

class ConversationsViewModel(
    private val conversationsRepository: ConversationsRepository
) : BaseViewModel() {

    private val _conversations = MutableLiveData<List<ConversationItem>>()
    val conversations: LiveData<List<ConversationItem>> = _conversations

    fun fetchConversations() {
        conversationsRepository.fetchConversations()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    _conversations.value = it
                },
                {
                    // TODO: Handle error
                }
            ).apply {
                compositeDisposable.add(this)
            }
    }
}
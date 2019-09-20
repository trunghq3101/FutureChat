package com.miller.conversations

import com.miller.common.base.BaseViewModel
import com.miller.common.utils.SchedulersUtils
import com.miller.conversations.repository.ConversationsRepository

/**
 * Created by Miller on 20/09/2019
 */

class ConversationsViewModel(
    private val conversationsRepository: ConversationsRepository
) : BaseViewModel() {

    fun fetchConversations() {
        conversationsRepository.fetchConversations()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {

                },
                {

                }
            ).apply {
                compositeDisposable.add(this)
            }
    }
}
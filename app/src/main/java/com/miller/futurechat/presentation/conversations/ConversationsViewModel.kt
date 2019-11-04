package com.miller.futurechat.presentation.conversations

import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.utils.SingleLiveEvent
import org.koin.core.inject

class ConversationsViewModel: BaseViewModel() {

    override val useCases: UseCases by inject()

    val toNewConversation = SingleLiveEvent<Boolean>()

    fun navigateToProfile() {
        navigate(ConversationsFragmentDirections.actionConversationsToProfile())
    }

    fun navigateToNewConversation() {
        toNewConversation.value = true
    }
}
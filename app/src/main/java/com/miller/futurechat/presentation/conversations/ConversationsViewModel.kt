package com.miller.futurechat.presentation.conversations

import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.base.BaseViewModel
import org.koin.core.inject

class ConversationsViewModel: BaseViewModel() {

    override val useCases: UseCases by inject()

    fun navigateToProfile() {
        navigate(ConversationsFragmentDirections.actionConversationsToProfile())
    }
}
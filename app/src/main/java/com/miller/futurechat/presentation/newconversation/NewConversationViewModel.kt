package com.miller.futurechat.presentation.newconversation

import androidx.lifecycle.MutableLiveData
import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.base.BaseViewModel
import org.koin.core.inject

class NewConversationViewModel : BaseViewModel() {
    override val useCases: UseCases by inject()

    val avatarUrl = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val defaultMsg = MutableLiveData<String>()

    fun saveNewConversation() {
        avatarUrl.value ?: return
        title.value ?: return
        defaultMsg.value ?: return
        useCases.createNewConversation(
            avatarUrl = avatarUrl.value!!,
            title = title.value!!,
            defaultMsg = defaultMsg.value!!
        )
    }
}
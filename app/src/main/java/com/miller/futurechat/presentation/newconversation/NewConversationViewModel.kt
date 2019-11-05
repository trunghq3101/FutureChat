package com.miller.futurechat.presentation.newconversation

import androidx.lifecycle.MutableLiveData
import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.utils.NavigationCommand
import com.miller.futurechat.utils.SchedulersUtils
import org.koin.core.inject

class NewConversationViewModel : BaseViewModel() {
    override val useCases: UseCases by inject()

    val avatarUrl = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val defaultMsg = MutableLiveData<String>()

    fun saveNewConversation() {
        if (avatarUrl.value == null) {
            avatarUrl.value = "https://picsum.photos/id/500/200/200"
        }
        title.value ?: return
        defaultMsg.value ?: return
        useCases.createNewConversation(
            avatarUrl = avatarUrl.value!!,
            title = title.value!!,
            defaultMsg = defaultMsg.value!!
        ).compose(SchedulersUtils.applyAsyncSchedulersCompletable())
            .subscribe(
                {
                    navCommands.value = NavigationCommand.Back
                },
                {
                    onLoadFail(it)
                }
            )
            .apply {
                addDisposable(this)
            }
    }
}
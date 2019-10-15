package com.miller.futurechat.presentation.conversations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.model.ConversationItem
import com.miller.futurechat.presentation.model.mapToPresentation

class ConversationsViewModel(
    private val useCases: UseCases
) : BaseViewModel() {

    private val _conversations = MutableLiveData<List<ConversationItem>>()
    val conversations: LiveData<List<ConversationItem>> = _conversations

    fun loadConversations() {
        useCases.getConversations()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .map { list ->
                list.map { it.mapToPresentation() }
            }
            .subscribe(
                {
                    Log.d("------>", " : $it")
                    _conversations.value = it
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }
}
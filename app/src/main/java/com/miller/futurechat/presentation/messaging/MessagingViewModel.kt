package com.miller.futurechat.presentation.messaging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.utils.EditTextBindingUtils
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.model.MessageItem
import com.miller.futurechat.presentation.model.mapToPresentation

class MessagingViewModel(
    private val useCases: UseCases
) : BaseViewModel() {

    val onInputTextExistListener = object : EditTextBindingUtils.OnTextExistListener {
        override fun onTextEmpty() {
            changeSendButtonVisibility(false)
        }

        override fun onTextExist() {
            changeSendButtonVisibility(true)
        }
    }

    var conversationId: String? = null

    /*
    Observables
     */
    private val _isSendBtnVisible = MutableLiveData<Boolean>().apply { value = false }
    val isSendBtnVisible: LiveData<Boolean> = _isSendBtnVisible
    private val _messages = MutableLiveData<List<MessageItem>>()
    val messages: LiveData<List<MessageItem>> = _messages

    val textMsg = MutableLiveData<String>()
    /*
    ============
     */

    fun changeSendButtonVisibility(isVisible: Boolean) {
        _isSendBtnVisible.value = isVisible
    }

    fun loadMsg() {
        conversationId?.let { id ->
            useCases.getMessages(id)
                .compose(SchedulersUtils.applyAsyncSchedulersSingle())
                .map { list ->
                    list.map { it.mapToPresentation() }
                }
                .subscribe(
                    {
                        Log.d("------>"," : $it")
                    },
                    {
                        onLoadFail(it)
                    }
                )
        }
    }

    fun sendMsg() {

    }
}
package com.miller.futurechat.presentation.messaging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.core.domain.model.Message
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.widget.MessagePagingDataLoader
import com.miller.futurechat.presentation.base.PagingViewModel
import com.miller.futurechat.utils.EditTextBindingUtils
import com.miller.futurechat.utils.SchedulersUtils
import org.koin.core.inject
import java.util.*

class MessagingViewModel : PagingViewModel<Message>() {

    override val pagingDataLoader: MessagePagingDataLoader by inject()
    override val useCases: UseCases by inject()

    val onInputTextExistListener = object : EditTextBindingUtils.OnTextExistListener {
        override fun onTextEmpty() {
            changeSendButtonVisibility(false)
        }

        override fun onTextExist() {
            changeSendButtonVisibility(true)
        }
    }

    private var userId: String? = null
    private var conversationId: String? = null

    /*
    Observables
     */
    private val _isSendBtnVisible = MutableLiveData<Boolean>().apply { value = false }
    val isSendBtnVisible: LiveData<Boolean> = _isSendBtnVisible

    val textMsg = MutableLiveData<String>()
    /*
    ============
     */

    fun changeSendButtonVisibility(isVisible: Boolean) {
        _isSendBtnVisible.value = isVisible
    }

    fun changeUserId(userId: String?) {
        this.userId = userId
    }

    fun loadMsg(userId: String?, conversationId: String) {
        userId ?: return
        this.userId = userId
        this.conversationId = conversationId
        pagingWrapper.value = pagingDataLoader.fetchPage(userId, conversationId)
    }

    fun sendMsg() {
        sendMsg(userId, conversationId, textMsg.value)
    }

    private fun sendMsg(userId: String?, convId: String?, text: String?) {
        userId ?: return
        convId ?: return
        text ?: return
        val newMsg = Message(
            contentText = text,
            senderId = userId,
            conversationId = convId,
            timestamp = Date().time
        )
        useCases.saveMessageLocal(newMsg)
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    Log.d("------>", " : saved")
                    textMsg.value = ""
                    sendMsgToRemote(newMsg.apply { id = it })
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }

    private fun sendMsgToRemote(newMsg: Message) {
        useCases.sendMessage(newMsg)
            .compose(SchedulersUtils.applyAsyncSchedulersCompletable())
            .subscribe(
                {
                    Log.d("------>", " : sent")
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }
}
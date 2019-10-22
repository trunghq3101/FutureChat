package com.miller.futurechat.presentation.messaging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.core.domain.model.Message
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState
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

    private var conversationId: String? = null

    /*
    Observables
     */
    private val _isSendBtnVisible = MutableLiveData<Boolean>().apply { value = false }
    val isSendBtnVisible: LiveData<Boolean> = _isSendBtnVisible

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    val textMsg = MutableLiveData<String>()
    /*
    ============
     */

    fun changeSendButtonVisibility(isVisible: Boolean) {
        _isSendBtnVisible.value = isVisible
    }

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

    fun loadMsg(conversationId: String) {
        this.conversationId = conversationId
        getAuthToken()?.let { userId ->
            pagingWrapper.value = pagingDataLoader.fetchPage(userId, conversationId)
        }
    }

    fun sendMsg() {
        getAuthToken()?.let { userId ->
            conversationId?.let { convId ->
                textMsg.value?.let { text ->
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
                                Log.d("------>"," : saved")
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
            }
        }
    }

    private fun sendMsgToRemote(newMsg: Message) {
        useCases.sendMessage(newMsg).compose(SchedulersUtils.applyAsyncSchedulersCompletable())
            .subscribe(
                {
                    Log.d("------>"," : sent")
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }

    private fun getAuthToken() = (authState.value as? AuthState.LoggedIn)?.token
}
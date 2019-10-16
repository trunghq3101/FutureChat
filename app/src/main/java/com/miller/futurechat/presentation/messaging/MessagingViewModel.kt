package com.miller.futurechat.presentation.messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.framework.model.MessageEntity
import com.miller.futurechat.framework.widget.MessagePagingDataLoader
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.presentation.model.MessageItem
import com.miller.futurechat.utils.EditTextBindingUtils
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.futurechat.utils.SingleLiveEvent
import com.miller.paging.LivePagingWrapper
import com.miller.paging.PagingDataLoader
import com.miller.paging.fetchPage
import org.koin.core.inject

class MessagingViewModel(
    private val pagingDataLoader: MessagePagingDataLoader
) : BaseViewModel() {

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

    private val pagingWrapper = SingleLiveEvent<LivePagingWrapper<MessageItem>>()

    val pagedList: LiveData<PagedList<MessageItem>> = Transformations.switchMap(pagingWrapper) {
        it.livePagedList
    }
    val networkState = Transformations.switchMap(pagingWrapper) {
        it.liveNetworkState
    }

    val textMsg = MutableLiveData<String>()
    /*
    ============
     */

    fun changeSendButtonVisibility(isVisible: Boolean) {
        _isSendBtnVisible.value = isVisible
    }

    fun loadMsg(conversationId: String) {
        this.conversationId = conversationId
        useCases.getAuthState()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    when (it) {
                        is AuthState.LoggedIn -> pagingDataLoader.fetchPage(
                            it.token,
                            conversationId
                        )
                        else -> showLoggedOutError()
                    }
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }

    fun sendMsg() {

    }

    private fun showLoggedOutError() {}
}
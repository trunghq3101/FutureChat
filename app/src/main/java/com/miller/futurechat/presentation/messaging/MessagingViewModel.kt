package com.miller.futurechat.presentation.messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miller.common.base.BaseViewModel
import com.miller.common.utils.EditTextBindingUtils

class MessagingViewModel : BaseViewModel() {

    val onInputTextExistListener = object : EditTextBindingUtils.OnTextExistListener {
        override fun onTextEmpty() {
            changeSendButtonVisibility(false)
        }

        override fun onTextExist() {
            changeSendButtonVisibility(true)
        }
    }

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

    fun sendMsg() {

    }
}
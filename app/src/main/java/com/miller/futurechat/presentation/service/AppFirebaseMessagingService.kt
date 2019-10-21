package com.miller.futurechat.presentation.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.miller.core.domain.model.Message
import com.miller.core.domain.model.Message.Companion.KEY_CONTENT_TEXT
import com.miller.core.domain.model.Message.Companion.KEY_CONVERSATION_ID
import com.miller.core.domain.model.Message.Companion.KEY_ID
import com.miller.core.domain.model.Message.Companion.KEY_SENDER_ID
import com.miller.core.domain.model.Message.Companion.KEY_TIMESTAMP
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.model.MessageEntity
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.utils.SchedulersUtils
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val useCases: UseCases by inject()
    private val compositeDisposable: CompositeDisposable by inject()

    override fun onMessageReceived(remoteMsg: RemoteMessage) {
        super.onMessageReceived(remoteMsg)
        remoteMsg.data.let {
            if (it.isNotEmpty()) {
                useCases.receiveMessage(
                    Message(
                        id = it[KEY_ID]?.toInt() ?: 0,
                        conversationId = it[KEY_CONVERSATION_ID] ?: "",
                        senderId = it[KEY_SENDER_ID] ?: "",
                        contentText = it[KEY_CONTENT_TEXT] ?: "",
                        timestamp = it[KEY_TIMESTAMP]?.toLong() ?: 0L
                    )
                ).compose(SchedulersUtils.applyAsyncSchedulersSingle())
                    .subscribe(
                        {
                            Log.d(
                                "----->",
                                "AppFirebaseMessagingService - onMessageReceived : saved"
                            )
                        },
                        { t -> t.printStackTrace() }
                    ).apply {
                        compositeDisposable.add(this)
                    }
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
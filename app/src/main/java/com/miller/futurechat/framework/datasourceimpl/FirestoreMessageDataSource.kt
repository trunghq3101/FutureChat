package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.MessageDataSource
import com.miller.core.domain.model.Message
import com.miller.futurechat.framework.firestore.CollectionsConstant.CONVERSATIONS
import com.miller.futurechat.framework.firestore.CollectionsConstant.MESSAGES
import com.miller.futurechat.utils.toItemList
import com.miller.utils.toSingle
import io.reactivex.Single

class FirestoreMessageDataSource(
    private val firestore: FirebaseFirestore
) : MessageDataSource {
    override fun readMessages(authToken: String, conversationId: String): Single<List<Message>> {
        return firestore.collection(CONVERSATIONS).document(conversationId).collection(MESSAGES)
            .get()
            .toSingle()
            .map {
                it.toItemList(Message::class.java)
            }
    }

}
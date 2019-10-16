package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.MessageDataSource
import com.miller.core.domain.model.Message
import com.miller.futurechat.framework.firestore.CollectionsConstant.CONVERSATIONS
import com.miller.futurechat.framework.firestore.CollectionsConstant.MESSAGES
import com.miller.futurechat.framework.model.MessageEntity
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.utils.toItemList
import com.miller.utils.toSingle
import io.reactivex.Single

class FirestoreMessageDataSource(
    private val firestore: FirebaseFirestore
) : MessageDataSource {

    override fun readPagingMessages(
        conversationId: String,
        lastItem: Message?
    ): Single<List<Message>> {
        return lastItem?.let {
            Single.create<List<Message>> { emitter ->
                firestore.collection(CONVERSATIONS).document(conversationId).collection(MESSAGES)
                    .document(it.id)
                    .get()
                    .addOnSuccessListener { docSnap ->
                        firestore.collection(CONVERSATIONS).document(conversationId)
                            .collection(MESSAGES).startAfter(docSnap)
                            .get()
                            .addOnSuccessListener { querySnap ->
                                querySnap.toItemList(MessageEntity::class.java).map {
                                    it.mapToDomain()
                                }.let {
                                    emitter.onSuccess(it)
                                }
                            }
                            .addOnFailureListener {
                                emitter.onError(it)
                            }
                    }
            }
        } ?: run {
            firestore.collection(CONVERSATIONS).document(conversationId).collection(MESSAGES).get()
                .toSingle()
                .map { querySnap ->
                    querySnap.toItemList(MessageEntity::class.java).map {
                        it.mapToDomain()
                    }
                }
        }

    }

    override fun readMessages(authToken: String, conversationId: String): Single<List<Message>> {
        return firestore.collection(CONVERSATIONS).document(conversationId).collection(MESSAGES)
            .get()
            .toSingle()
            .map {
                it.toItemList(Message::class.java)
            }
    }

}
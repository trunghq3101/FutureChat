package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.miller.core.data.datasource.RemoteMessageDataSource
import com.miller.core.domain.model.Message
import com.miller.futurechat.framework.firestore.CollectionsConstant.CONVERSATIONS
import com.miller.futurechat.framework.firestore.CollectionsConstant.MESSAGES
import com.miller.futurechat.framework.firestore.CollectionsConstant.MessagesConstant.TIMESTAMP
import com.miller.futurechat.framework.model.MessageEntity
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.utils.toCompletable
import com.miller.futurechat.utils.toItemList
import com.miller.paging.PagingBoundaryCallback.Companion.PAGE_SIZE
import com.miller.futurechat.utils.toSingle
import io.reactivex.Completable
import io.reactivex.Single

class FirestoreMessageDataSource(
    private val firestore: FirebaseFirestore
) : RemoteMessageDataSource {

    override fun readPagingMessagesBefore(
        convId: String,
        firstMsgId: Int
    ): Single<List<Message>> {
        return Single.create<List<Message>> { emitter ->
            queryOneMsg(convId, firstMsgId)
                .get()
                .addOnSuccessListener { docSnap ->
                    queryOldestMessages(convId)
                        .endBefore(docSnap)
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
    }

    override fun readPagingMessagesAfter(
        conversationId: String,
        lastMsgId: Int?
    ): Single<List<Message>> {
        return lastMsgId?.let {
            Single.create<List<Message>> { emitter ->
                queryOneMsg(conversationId, it)
                    .get()
                    .addOnSuccessListener { docSnap ->
                        queryOldestMessages(conversationId)
                            .limit(PAGE_SIZE.toLong())
                            .startAfter(docSnap)
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
            queryOldestMessages(conversationId)
                .limit(PAGE_SIZE.toLong())
                .get()
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

    override fun createMessage(msg: Message): Completable {
        return queryOneMsg(msg.conversationId, msg.id).set(msg).toCompletable()
    }

    private fun queryOneMsg(convId: String, msgId: Int) =
        firestore.collection(CONVERSATIONS)
            .document(convId)
            .collection(MESSAGES)
            .document("$msgId")

    private fun queryOldestMessages(convId: String) = firestore.collection(CONVERSATIONS)
        .document(convId)
        .collection(MESSAGES)
        .orderBy(TIMESTAMP, Query.Direction.DESCENDING)

    private fun queryMessages(convId: String) = firestore.collection(CONVERSATIONS)
        .document(convId)
        .collection(MESSAGES)

}
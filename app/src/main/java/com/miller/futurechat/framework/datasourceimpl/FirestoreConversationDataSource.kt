package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.ConversationDataSource
import com.miller.core.domain.model.Conversation
import com.miller.futurechat.framework.firestore.CollectionsConstant.CONVERSATIONS
import com.miller.futurechat.framework.firestore.CollectionsConstant.ConversationsConstant.FOLLOWERS
import com.miller.futurechat.framework.model.ConversationEntity
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.utils.toItemList
import com.miller.paging.PagingBoundaryCallback
import com.miller.paging.PagingBoundaryCallback.Companion.PAGE_SIZE
import com.miller.utils.toSingle
import io.reactivex.Single

class FirestoreConversationDataSource(
    private val firestore: FirebaseFirestore
) : ConversationDataSource {

    override fun readAll(authToken: String): Single<List<Conversation>> {
        return firestore.collection(CONVERSATIONS).whereArrayContains(FOLLOWERS, authToken)
            .get()
            .toSingle()
            .map { querySnap ->
                querySnap.toItemList(ConversationEntity::class.java).map { it.mapToDomain() }
            }
    }

    override fun readPaging(authToken: String, lastConvId: String?): Single<List<Conversation>> {
        return lastConvId?.let {
            Single.create<List<Conversation>> { emitter ->
                firestore.collection(CONVERSATIONS)
                    .document(it)
                    .get()
                    .addOnSuccessListener { docSnap ->
                        firestore.collection(CONVERSATIONS)
                            .whereArrayContains(FOLLOWERS, authToken)
                            .startAfter(docSnap)
                            .limit(PAGE_SIZE.toLong())
                            .get()
                            .addOnSuccessListener { querySnap ->
                                querySnap.toItemList(ConversationEntity::class.java).map {
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
            firestore.collection(CONVERSATIONS)
                .whereArrayContains(FOLLOWERS, authToken)
                .limit(PAGE_SIZE.toLong())
                .get()
                .toSingle()
                .map { querySnap ->
                    querySnap.toItemList(ConversationEntity::class.java).map { it.mapToDomain() }
                }
        }
    }
}
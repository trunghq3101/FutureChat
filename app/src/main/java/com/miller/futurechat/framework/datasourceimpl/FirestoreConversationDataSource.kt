package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.ConversationDataSource
import com.miller.core.domain.model.Conversation
import com.miller.futurechat.framework.firestore.CollectionsConstant.CONVERSATIONS
import com.miller.futurechat.framework.firestore.CollectionsConstant.ConversationsConstant.FOLLOWERS
import com.miller.futurechat.utils.toItemList
import com.miller.utils.toSingle
import io.reactivex.Single

class FirestoreConversationDataSource(
    private val firestore: FirebaseFirestore
) : ConversationDataSource {

    override fun readAll(authToken: String): Single<List<Conversation>> {
        return firestore.collection(CONVERSATIONS).whereArrayContains(FOLLOWERS, authToken).get()
            .toSingle().map {
            it.toItemList(Conversation::class.java)
        }
    }
}
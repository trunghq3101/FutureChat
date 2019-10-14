package com.miller.datasource.firebase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.CollectionsConstant.CONVERSATIONS
import com.miller.CollectionsConstant.ConversationsConstant.FOLLOWERS
import com.miller.CollectionsConstant.MESSAGES
import com.miller.conversations.model.ConversationItem
import com.miller.model.ConversationEntity
import com.miller.model.MessageEntity
import com.miller.utils.toItemList
import com.miller.utils.toSingle
import io.reactivex.Single

/**
 * Created by Miller on 19/09/2019
 */

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    fun fetchConversations(uid: String): Single<List<ConversationItem>> {
        return firestore.collection(CONVERSATIONS).whereArrayContains(FOLLOWERS, uid).get()
            .toSingle()
            .map { querySnap ->
                querySnap.toItemList(ConversationEntity::class.java).map {
                    it.toItem()
                }
            }
    }

    fun saveMessage(conversationId: String, msg: MessageEntity): Single<DocumentReference> {
        return firestore.collection(CONVERSATIONS)
            .document(conversationId)
            .collection(MESSAGES)
            .add(msg)
            .toSingle()
    }

}
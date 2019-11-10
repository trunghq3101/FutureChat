package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.NewConversationDataSource
import com.miller.core.domain.model.NewConversation
import com.miller.futurechat.framework.firestore.CollectionsConstant.CONVERSATIONS
import com.miller.futurechat.framework.firestore.CollectionsConstant.ConversationsConstant.AVATAR_URL
import com.miller.futurechat.framework.firestore.CollectionsConstant.ConversationsConstant.FOLLOWERS
import com.miller.futurechat.framework.firestore.CollectionsConstant.ConversationsConstant.LAST_MESSAGE
import com.miller.futurechat.framework.firestore.CollectionsConstant.ConversationsConstant.TITLE
import com.miller.futurechat.utils.toSingle
import io.reactivex.Single

class FirestoreNewConversationDataSource(
    private val firestore: FirebaseFirestore
) : NewConversationDataSource {

    override fun create(cv: NewConversation): Single<String> {
        return firestore.collection(CONVERSATIONS)
            .add(hashMapOf(
                AVATAR_URL to cv.avatarUrl,
                FOLLOWERS to cv.followers,
                LAST_MESSAGE to cv.lastMessage,
                TITLE to cv.title
            ))
            .toSingle()
            .map { it.id }
    }

}
package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.UserDataSource
import com.miller.futurechat.framework.firestore.CollectionsConstant.USERS
import com.miller.futurechat.framework.firestore.CollectionsConstant.UsersConstant.CONVERSATIONS
import com.miller.futurechat.utils.toCompletable
import io.reactivex.Completable

class FirestoreUserDataSource(
    private val firestore: FirebaseFirestore
) : UserDataSource {
    override fun addConversation(userId: String, convId: String): Completable {
        return firestore.collection(USERS)
            .document(userId)
            .update(CONVERSATIONS, FieldValue.arrayUnion(convId))
            .toCompletable()
    }

}
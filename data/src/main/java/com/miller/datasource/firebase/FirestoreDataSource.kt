package com.miller.datasource.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.CollectionsConstant.NOTIFICATION_TOKENS
import com.miller.CollectionsConstant.NotificationTokensConstant.TOKENS
import com.miller.model.TokenEntity
import com.miller.model.TokenEntity.Companion.KEY_USER_ID
import com.miller.utils.createNew
import com.miller.utils.updateArray

/**
 * Created by Miller on 19/09/2019
 */

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    fun saveRegistrationToken(token: String, uid: String): Task<DocumentReference> {
        val query = firestore.collection(NOTIFICATION_TOKENS).whereEqualTo(KEY_USER_ID, uid)
        return query.get()
            .onSuccessTask {
                it?.documents?.let { listSnapshot ->
                    if (listSnapshot.size == 0) {
                        firestore.createNew(NOTIFICATION_TOKENS, TokenEntity(uid, listOf(token)))
                    } else {
                        firestore.updateArray(
                            "$NOTIFICATION_TOKENS/${listSnapshot[0].id}",
                            TOKENS,
                            token
                        ).continueWith {
                            firestore.collection(NOTIFICATION_TOKENS).document(listSnapshot[0].id)
                        }
                    }
                } ?: run {
                    firestore.createNew(NOTIFICATION_TOKENS, TokenEntity(uid, listOf(token)))
                }
            }
    }

}
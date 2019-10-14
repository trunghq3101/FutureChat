package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.NotificationTokenDataSource
import com.miller.core.domain.model.NotificationToken
import com.miller.futurechat.framework.firestore.CollectionsConstant
import com.miller.futurechat.framework.firestore.CollectionsConstant.NOTIFICATION_TOKENS
import com.miller.futurechat.framework.firestore.CollectionsConstant.NotificationTokensConstant.USER_ID
import com.miller.futurechat.utils.createNew
import com.miller.futurechat.utils.updateArray
import io.reactivex.Single

class FirestoreNotificationTokenDataSource(
    private val firestore: FirebaseFirestore
) : NotificationTokenDataSource {

    override fun add(token: String, authToken: String): Single<NotificationToken> {
        val query = firestore.collection(NOTIFICATION_TOKENS).whereEqualTo(
            USER_ID, authToken
        )
        val newNotificationToken = NotificationToken(authToken, listOf(token))
        return Single.create<NotificationToken> { emitter ->
            query.get()
                .continueWith { task ->
                    task.result?.documents?.let { listSnapshot ->
                        if (listSnapshot.size == 0) {
                            firestore.createNew(
                                NOTIFICATION_TOKENS,
                                newNotificationToken
                            )
                        } else {
                            firestore.updateArray(
                                "${NOTIFICATION_TOKENS}/${listSnapshot[0].id}",
                                CollectionsConstant.NotificationTokensConstant.TOKENS,
                                token
                            )
                        }
                    } ?: run {
                        firestore.createNew(
                            NOTIFICATION_TOKENS,
                            newNotificationToken
                        )
                    }
                }
                .addOnSuccessListener {
                    emitter.onSuccess(newNotificationToken)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}
package com.miller.datasource.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.CollectionsConstant

/**
 * Created by Miller on 19/09/2019
 */

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
) {
    fun saveRegistrationToken(token: String): Task<Void> =
        firestore.collection(CollectionsConstant.USERS)
            .document("trunghq")
            .update(
                CollectionsConstant.UserConstant.NOTIFICATION_TOKENS,
                FieldValue.arrayUnion(token)
            )
}
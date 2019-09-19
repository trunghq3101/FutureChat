package com.miller.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.data.CollectionsConstant.USERS
import com.miller.data.CollectionsConstant.UserConstant.NOTIFICATION_TOKENS

/**
 * Created by Miller on 18/09/2019
 */

class FirebaseRepository(
    private val firestore: FirebaseFirestore
) {
    fun saveRegistrationToken(token: String): Task<Void> {
        return firestore.collection(USERS)
            .document("trunghq")
            .update(NOTIFICATION_TOKENS, FieldValue.arrayUnion(token))
    }
}
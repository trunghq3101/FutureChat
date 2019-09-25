package com.miller.repository

import com.google.firebase.firestore.DocumentReference
import com.miller.datasource.firebase.FirestoreDataSource
import com.miller.utils.toSingle
import io.reactivex.Single

/**
 * Created by Miller on 18/09/2019
 */

class FirebaseRepository(
    private val firestoreDataSource: FirestoreDataSource
) {
    fun saveRegistrationToken(token: String, uid: String): Single<DocumentReference> {
        return firestoreDataSource.saveRegistrationToken(token, uid).toSingle()
    }
}
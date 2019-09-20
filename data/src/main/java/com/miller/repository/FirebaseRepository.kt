package com.miller.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.miller.datasource.firebase.FirestoreDataSource
import com.miller.datasource.sharePref.SharedPrefDataSource

/**
 * Created by Miller on 18/09/2019
 */

class FirebaseRepository(
    private val firestoreDataSource: FirestoreDataSource
) {
    fun saveRegistrationToken(token: String, uid: String): Task<DocumentReference> {
        return firestoreDataSource.saveRegistrationToken(token, uid)
    }
}
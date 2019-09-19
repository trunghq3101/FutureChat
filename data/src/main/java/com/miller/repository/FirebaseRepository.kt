package com.miller.repository

import com.google.android.gms.tasks.Task
import com.miller.datasource.firebase.FirestoreDataSource

/**
 * Created by Miller on 18/09/2019
 */

class FirebaseRepository(
    private val firestoreDataSource: FirestoreDataSource
) {
    fun saveRegistrationToken(token: String): Task<Void> {
        return firestoreDataSource.saveRegistrationToken(token)
    }
}
package com.miller.futurechat.livemessaging.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.futurechat.livemessaging.data.model.UserEntity
import io.reactivex.Single

/**
 * Created by Miller on 18/09/2019
 */

class FirebaseRepository(
    private val firestore: FirebaseFirestore
) {
    fun saveRegistrationToken(token: String): Single<Void> {
        return Single.create<Void> { emitter ->
            firestore.collection("users")
                .document("trunghq")
                .set(UserEntity(listOf(token)))
                .addOnSuccessListener {
                    emitter.onSuccess(it)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}
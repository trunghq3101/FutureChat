package com.miller.futurechat

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.miller.data.repository.FirebaseRepository
import org.koin.android.ext.android.inject

class AppFirebaseMessagingService : FirebaseMessagingService() {

    private val firebaseRepository: FirebaseRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        firebaseRepository.saveRegistrationToken(token)
            .addOnCompleteListener {
                Log.d("----->","AppFirebaseMessagingService - sendRegistrationToServer : Success")
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}
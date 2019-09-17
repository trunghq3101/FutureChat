package com.miller.futurechat.livemessaging.data

import com.google.firebase.messaging.FirebaseMessagingService

class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {

    }
}
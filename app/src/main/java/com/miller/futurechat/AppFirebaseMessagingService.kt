package com.miller.futurechat

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d("----->","AppFirebaseMessagingService - onMessageReceived : ")
        super.onMessageReceived(p0)
    }
}
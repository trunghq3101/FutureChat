package com.miller.futurechat.livemessaging.data

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.miller.futurechat.livemessaging.data.repository.FirebaseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class AppFirebaseMessagingService : FirebaseMessagingService() {

    private val firebaseRepository: FirebaseRepository by inject()
    private val compositeDisposable: CompositeDisposable by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun sendRegistrationToServer(token: String) {
        firebaseRepository.saveRegistrationToken(token)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("----->","AppFirebaseMessagingService - sendRegistrationToServer : Success")
                },
                {
                    it.printStackTrace()
                }
            )
            .apply {
                compositeDisposable.add(this)
            }
    }
}
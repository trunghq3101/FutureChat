package com.miller.futurechat.utils.ext

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.iid.FirebaseInstanceId
import com.miller.futurechat.MainActivity

/**
 * Created by Miller on 19/09/2019
 */

fun AppCompatActivity.registerFCMInstanceId() {
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.d("----->","MainActivity - onCreate : failed")
                return@addOnCompleteListener
            }
            Log.d("----->","MainActivity - onCreate : ${it.result?.token}")
        }
        .addOnFailureListener {
            it.printStackTrace()
        }
}

fun AppCompatActivity.openAuthenActivity() {
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build())
    startActivityForResult(
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build(),
        MainActivity.REQ_CODE_SIGN_IN
    )
}
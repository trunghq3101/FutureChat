package com.miller.futurechat.utils.ext

import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.miller.futurechat.MainActivity

/**
 * Created by Miller on 19/09/2019
 */

fun AppCompatActivity.openAuthenActivity() {
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    startActivityForResult(
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build(),
        MainActivity.REQ_CODE_SIGN_IN
    )
}

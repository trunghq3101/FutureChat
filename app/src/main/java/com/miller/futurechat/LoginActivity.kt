package com.miller.futurechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

/**
 * Created by Miller on 25/09/2019
 */

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private val authIntent by inject<Intent>(named("authIntent"))
    private val mainIntent by inject<Intent>(named("mainIntent"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        observeField()

        if (viewModel.readUserIdFromSharedPref().isNotEmpty()) {
            loggedIn()
        } else {
            openFirebaseAuthActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Handle result received from authentication activity
        if (requestCode == REQ_CODE_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                FirebaseAuth.getInstance().currentUser?.uid?.let {
                    viewModel.saveUserIdToSharedPref(it)
                }
                loggedIn()
            } else {
                if (response == null) {
                    finish()
                }
            }
        }
    }

    private fun observeField() {
        with(viewModel) {
            fcmTokenSaved.observe(this@LoginActivity, Observer {
                if (it) startActivity(mainIntent)
            })
        }
    }

    private fun openFirebaseAuthActivity() {
        startActivityForResult(
            authIntent,
            REQ_CODE_SIGN_IN
        )
    }

    private fun registerFCMInstanceId() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                it.result?.token?.let { token ->
                    viewModel.saveFCMToken(token)
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun loggedIn() {
        registerFCMInstanceId()
    }

    companion object {
        const val REQ_CODE_SIGN_IN = 111
    }
}
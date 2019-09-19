package com.miller.futurechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.miller.conversations.ConversationsFragment
import com.miller.futurechat.utils.ext.openAuthenActivity
import com.miller.futurechat.utils.ext.openFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openAuthenActivity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                FirebaseAuth.getInstance().currentUser?.uid?.let {
                    viewModel.saveUserIdToSharedPref(it)
                }
                registerFCMInstanceId()
                openFragment(ConversationsFragment.newInstance(), R.id.container)
            } else {
                if (response == null) {
                    finish()
                }
            }
        }
    }

    private fun registerFCMInstanceId() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d("----->", "MainActivity - onCreate : failed")
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

    companion object {
        const val REQ_CODE_SIGN_IN = 111
    }
}

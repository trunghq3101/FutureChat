package com.miller.futurechat.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.R
import com.miller.futurechat.presentation.blank.BlankFragmentDirections
import com.miller.futurechat.presentation.conversations.ConversationsFragmentDirections
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val authIntent by inject<Intent>(named("authIntent"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadLoggedInStatus()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra(EXTRA_CONVERSATION_ID)?.let {
            hostNavController().navigate(ConversationsFragmentDirections.actionConversationsToMessaging(it))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loggedInStatus.observe(this, Observer {
            when (it) {
                is AuthState.LoggedIn ->
                    hostNavController().navigate(BlankFragmentDirections.actionBlankToConversations())
                is AuthState.LoggedOut -> startActivityForResult(
                    authIntent,
                    REQ_CODE_SIGN_IN
                )
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        hostNavController().navigateUp()
        return super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Handle result received from authentication activity
        if (requestCode == REQ_CODE_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                FirebaseAuth.getInstance().currentUser?.uid?.let {
                    viewModel.saveAuthToken(it)
                    registerFCMInstanceId()
                } ?: run {
                    Log.d("----->", "MainActivity - onActivityResult : User not available")
                }
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
                    Log.d("----->", "MainActivity - registerFCMInstanceId : Unsuccessfully")
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

    private fun hostNavController() = findNavController(R.id.nav_host_fragment)

    companion object {
        const val REQ_CODE_SIGN_IN = 111
        const val EXTRA_CONVERSATION_ID = "conversationId"
    }
}

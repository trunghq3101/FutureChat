package com.miller.futurechat.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.R
import com.miller.futurechat.presentation.blank.BlankFragmentDirections
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val authIntent by inject<Intent>(named("authIntent"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.loadAuthState()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra(EXTRA_CONVERSATION_ID)?.let {
            hostNavController().navigate(R.id.action_to_messaging, Bundle().apply {
                putString(EXTRA_CONVERSATION_ID, it)
            })
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.authState.observe(this, Observer {
            when (it.getContentIfNotHandled()) {
                is AuthState.LoggedIn -> {
                    if (hostNavController().currentDestination?.id == R.id.blankFragment) hostNavController().navigate(
                        BlankFragmentDirections.actionBlankToConversations()
                    )
                    viewModel.loadUserInfo()
                }
                is AuthState.LoggedOut -> {
                    if (hostNavController().currentDestination?.id != R.id.blankFragment) hostNavController().navigate(
                        R.id.action_to_blank
                    )
                    startActivityForResult(
                        authIntent,
                        REQ_CODE_SIGN_IN
                    )
                }
            }
        })
        viewModel.signingOut.observe(this, Observer {
            if (it) AuthUI.getInstance().signOut(this)
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
                viewModel.registerFCMInstanceId()
            } else {
                if (response == null) {
                    finish()
                }
            }
        }
    }

    private fun hostNavController() = findNavController(R.id.nav_host_fragment)

    companion object {
        const val REQ_CODE_SIGN_IN = 111
        const val EXTRA_CONVERSATION_ID = "conversationId"
    }
}

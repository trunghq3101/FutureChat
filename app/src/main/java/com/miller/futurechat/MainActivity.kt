package com.miller.futurechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.IdpResponse
import com.miller.futurechat.utils.ext.openAuthenActivity
import com.miller.futurechat.utils.ext.registerFCMInstanceId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerFCMInstanceId()
        openAuthenActivity()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                Log.d("----->","MainActivity - onActivityResult : success")
            } else {
                if (response == null) {
                    finish()
                }
            }
        }
    }

    companion object {
        const val REQ_CODE_SIGN_IN = 111
    }
}

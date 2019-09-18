package com.miller.futurechat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.iid.FirebaseInstanceId

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}

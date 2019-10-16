package com.miller.futurechat.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.miller.futurechat.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)?.navController?.navigateUp()
        return super.onSupportNavigateUp()
    }
}

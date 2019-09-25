package com.miller.futurechat.di

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.miller.futurechat.MainActivity
import com.miller.futurechat.di.IntentModule.AUTH_INTENT
import com.miller.futurechat.di.IntentModule.MAIN_INTENT
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Miller on 25/09/2019
 */

val intentModule = module {

    // Intent to start Auth UI
    single(named(AUTH_INTENT)) {
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(listOf(get(named("email"))))
            .build()
    }

    single(named(MAIN_INTENT)) { Intent(get(), MainActivity::class.java) }
}

object IntentModule {
    const val AUTH_INTENT = "authIntent"
    const val MAIN_INTENT = "mainIntent"
}
package com.miller.futurechat.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.datasource.firebase.FirestoreDataSource
import com.miller.datasource.sharePref.SharedPrefApi
import com.miller.datasource.sharePref.SharedPrefDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Miller on 25/09/2019
 */

val dataSourceModule = module {

    // Email Provider for Firebase Auth
    single(named("email")) { AuthUI.IdpConfig.EmailBuilder().build() }

    single { FirebaseFirestore.getInstance() }
    single { SharedPrefApi(get()) }

    single { FirestoreDataSource(get()) }
    single { SharedPrefDataSource(get()) }
}
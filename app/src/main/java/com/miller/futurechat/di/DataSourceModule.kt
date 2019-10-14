package com.miller.futurechat.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.AuthenticationDataSource
import com.miller.core.data.datasource.ConversationDataSource
import com.miller.core.data.datasource.NotificationTokenDataSource
import com.miller.futurechat.framework.datasourceimpl.FirestoreConversationDataSource
import com.miller.futurechat.framework.datasourceimpl.FirestoreNotificationTokenDataSource
import com.miller.futurechat.framework.datasourceimpl.PrefAuthenticationDataSource
import com.miller.futurechat.framework.sharedPref.PrefHelper
import com.miller.futurechat.framework.sharedPref.SharedPrefApi
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
    single { PrefHelper(get()) }

    single<ConversationDataSource> { FirestoreConversationDataSource(get()) }
    single<NotificationTokenDataSource> { FirestoreNotificationTokenDataSource(get()) }
    single<AuthenticationDataSource> { PrefAuthenticationDataSource(get()) }
}
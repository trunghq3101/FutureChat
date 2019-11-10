package com.miller.futurechat.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.*
import com.miller.futurechat.framework.datasourceimpl.*
import com.miller.futurechat.framework.db.AppDatabase
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
    single { FirebaseAuth.getInstance() }
    single { SharedPrefApi(get()) }
    single { PrefHelper(get()) }
    single { AppDatabase.create(get()).Dao() }

    single<ConversationDataSource> { FirestoreConversationDataSource(get()) }
    single<NotificationTokenDataSource> { FirestoreNotificationTokenDataSource(get()) }
    single<LocalAuthDataSource> { FirebaseAuthDataSource(get()) }
    single<RemoteAuthDataSource> { FirestoreAuthDataSource(get()) }
    single<RemoteMessageDataSource> { FirestoreMessageDataSource(get()) }
    single<LocalMessageDataSource> { RoomDbMessageDataSource(get()) }
    single<NewConversationDataSource> { FirestoreNewConversationDataSource(get()) }
    single<UserDataSource> { FirestoreUserDataSource(get()) }
}
package com.miller.futurechat.di

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.conversations.repository.ConversationsRepository
import com.miller.datasource.firebase.FirestoreDataSource
import com.miller.datasource.sharePref.SharedPrefApi
import com.miller.datasource.sharePref.SharedPrefDataSource
import com.miller.repository.ConversationsRepositoryImpl
import com.miller.repository.FirebaseRepository
import com.miller.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

/**
 * Created by Miller on 18/09/2019
 */

val repositoryModule = module {
    factory { CompositeDisposable() }

    single { FirebaseFirestore.getInstance() }
    single { SharedPrefApi(get()) }

    single { FirestoreDataSource(get()) }
    single { SharedPrefDataSource(get()) }

    single { FirebaseRepository(get()) }
    single { UserRepository(get()) }
    single<ConversationsRepository> { ConversationsRepositoryImpl() }
}
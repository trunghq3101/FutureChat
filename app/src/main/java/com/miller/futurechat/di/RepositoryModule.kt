package com.miller.futurechat.di

import com.miller.conversations.repository.ConversationsRepository
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

    single { FirebaseRepository(get()) }
    single { UserRepository(get()) }
    single<ConversationsRepository> { ConversationsRepositoryImpl(get(), get()) }
}
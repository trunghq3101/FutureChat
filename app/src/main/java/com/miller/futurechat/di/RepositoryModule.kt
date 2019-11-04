package com.miller.futurechat.di

import com.miller.core.data.repository.*
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

/**
 * Created by Miller on 18/09/2019
 */

val repositoryModule = module {
    factory { CompositeDisposable() }

    single { ConversationRepository(get()) }
    single { NotificationTokenRepository(get()) }
    single { AuthenticationRepository(get(), get()) }
    single { MessageRepository(get(), get()) }
    single { NewConversationRepository(get()) }
}
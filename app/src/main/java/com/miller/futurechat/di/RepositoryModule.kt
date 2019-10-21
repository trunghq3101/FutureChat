package com.miller.futurechat.di

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.ConversationRepository
import com.miller.core.data.repository.MessageRepository
import com.miller.core.data.repository.NotificationTokenRepository
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
}
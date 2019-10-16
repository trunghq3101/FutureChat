package com.miller.futurechat.di

import com.miller.core.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    single {
        UseCases(
            GetConversations(get(), get()),
            GetAuthState(get()),
            SaveAuthToken(get()),
            AddNotificationToken(get()),
            GetMessages(get(), get()),
            GetPagingMessages(get())
        )
    }
}
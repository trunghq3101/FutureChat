package com.miller.futurechat.di

import com.miller.core.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    single {
        UseCases(
            LoadUserInfo(get()),
            AddNotificationToken(get(), get()),
            LoadPagedMessagesAfter(get()),
            LoadPagedMessagesBefore(get()),
            LoadPagedConversations(get(), get()),
            SendMessage(get()),
            ReceiveMessage(get()),
            SaveMessageLocal(get())
        )
    }
}
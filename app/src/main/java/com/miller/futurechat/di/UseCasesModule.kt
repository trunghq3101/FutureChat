package com.miller.futurechat.di

import com.miller.core.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    single {
        UseCases(
            GetConversations(get(), get()),
            GetAuthState(get()),
            SaveAuthToken(get()),
            GetUserInfo(get()),
            SignOut(get()),
            AddNotificationToken(get()),
            GetMessages(get(), get()),
            GetPagingMessagesAfter(get()),
            GetPagingMessagesBefore(get()),
            GetPagingConversations(get(), get()),
            SendMessage(get()),
            ReceiveMessage(get())
        )
    }
}
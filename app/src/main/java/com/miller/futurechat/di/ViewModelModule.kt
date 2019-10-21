package com.miller.futurechat.di

import com.miller.futurechat.framework.widget.ConversationPagingDataLoader
import com.miller.futurechat.framework.widget.MessagePagingDataLoader
import com.miller.futurechat.presentation.MainViewModel
import com.miller.futurechat.presentation.conversations.ConversationsViewModel
import com.miller.futurechat.presentation.login.LoginViewModel
import com.miller.futurechat.presentation.messaging.MessagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Miller on 19/09/2019
 */

val viewModelModule = module {
    factory { MessagePagingDataLoader(get(), get()) }
    factory { ConversationPagingDataLoader(get(), get()) }

    viewModel { LoginViewModel() }
    viewModel { MainViewModel() }
    viewModel { ConversationsViewModel() }
    viewModel { MessagingViewModel() }
}
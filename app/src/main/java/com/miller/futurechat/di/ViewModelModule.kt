package com.miller.futurechat.di

import com.miller.conversations.ConversationsViewModel
import com.miller.futurechat.MainViewModel
import com.miller.messaging.MessagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Miller on 19/09/2019
 */

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ConversationsViewModel(get()) }
    viewModel { MessagingViewModel() }
}
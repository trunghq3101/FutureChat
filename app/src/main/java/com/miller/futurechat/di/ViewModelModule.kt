package com.miller.futurechat.di

import com.miller.futurechat.presentation.conversations.ConversationsViewModel
import com.miller.futurechat.presentation.login.LoginViewModel
import com.miller.futurechat.presentation.messaging.MessagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Miller on 19/09/2019
 */

val viewModelModule = module {
    viewModel { LoginViewModel() }
    viewModel { ConversationsViewModel() }
    viewModel { MessagingViewModel() }
}
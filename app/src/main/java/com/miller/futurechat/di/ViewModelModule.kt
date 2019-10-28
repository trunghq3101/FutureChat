package com.miller.futurechat.di

import com.miller.futurechat.framework.widget.MessagePagingDataLoader
import com.miller.futurechat.presentation.MainViewModel
import com.miller.futurechat.presentation.conversations.ConversationsViewModel
import com.miller.futurechat.presentation.conversations.topicfollowing.TopicFollowingPagingDataLoader
import com.miller.futurechat.presentation.conversations.topicfollowing.TopicFollowingViewModel
import com.miller.futurechat.presentation.conversations.topicnewest.TopicNewestPagingDataLoader
import com.miller.futurechat.presentation.conversations.topicnewest.TopicNewestViewModel
import com.miller.futurechat.presentation.messaging.MessagingViewModel
import com.miller.futurechat.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Miller on 19/09/2019
 */

val viewModelModule = module {
    factory { MessagePagingDataLoader(get(), get()) }
    factory { TopicFollowingPagingDataLoader(get(), get(), get()) }
    factory { TopicNewestPagingDataLoader(get(), get(), get()) }

    viewModel { MainViewModel() }
    viewModel { ConversationsViewModel() }
    viewModel { MessagingViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { TopicFollowingViewModel() }
    viewModel { TopicNewestViewModel() }
}
package com.miller.futurechat.presentation.conversations.topicfollowing

import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.base.BaseViewModel
import org.koin.core.inject

/**
 * Created by Miller on 24/10/2019
 */

class TopicFollowingViewModel : BaseViewModel() {
    override val useCases: UseCases by inject()

}
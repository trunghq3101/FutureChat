package com.miller.futurechat.presentation.conversations.topicfollowing

import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentTopicFollowingBinding
import com.miller.futurechat.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Miller on 24/10/2019
 */

class TopicFollowingFragment : BaseFragment<FragmentTopicFollowingBinding, TopicFollowingViewModel>() {
    override val layoutId: Int = R.layout.fragment_topic_following
    override val viewModel: TopicFollowingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

}
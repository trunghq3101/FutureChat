package com.miller.futurechat.presentation.conversations.topicfollowing

import androidx.recyclerview.widget.RecyclerView
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentTopicFollowingBinding
import com.miller.futurechat.presentation.conversations.BaseConversationFragment
import kotlinx.android.synthetic.main.fragment_topic_following.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Miller on 24/10/2019
 */

class TopicFollowingFragment : BaseConversationFragment<FragmentTopicFollowingBinding, TopicFollowingViewModel>() {
    override val layoutId: Int = R.layout.fragment_topic_following
    override val viewModel: TopicFollowingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    override val recyclerView: RecyclerView
        get() = rvFollowingTopics
    override val conversationAdapter: TopicFollowingAdapter by lazy {
        TopicFollowingAdapter()
    }

    companion object {
        fun newInstance() = TopicFollowingFragment()
    }
}
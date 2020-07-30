package com.miller.futurechat.presentation.conversations.topicnewest

import androidx.recyclerview.widget.RecyclerView
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentTopicNewestBinding
import com.miller.futurechat.presentation.conversations.BaseConversationAdapter
import com.miller.futurechat.presentation.conversations.BaseConversationFragment
import kotlinx.android.synthetic.main.fragment_topic_newest.*
import org.koin.android.ext.android.inject

class TopicNewestFragment :
    BaseConversationFragment<FragmentTopicNewestBinding, TopicNewestViewModel>() {
    override val conversationAdapter: BaseConversationAdapter by lazy {
        TopicNewestAdapter()
    }
    override val recyclerView: RecyclerView
        get() = rvNewestTopics
    override val layoutId: Int = R.layout.fragment_topic_newest
    override val viewModel: TopicNewestViewModel by inject()
    override val bindingVar: Int = BR.viewModel

}
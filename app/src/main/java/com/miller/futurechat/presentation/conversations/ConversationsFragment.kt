package com.miller.futurechat.presentation.conversations

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentConversationsBinding
import com.miller.futurechat.presentation.MainViewModel
import com.miller.futurechat.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_conversations.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConversationsFragment : BaseFragment<FragmentConversationsBinding, ConversationsViewModel>() {
    override val layoutId: Int = R.layout.fragment_conversations
    override val viewModel: ConversationsViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val topicPagerAdapter: TopicPagerAdapter by lazy {
        TopicPagerAdapter(childFragmentManager)
    }

    override fun initView() {
        super.initView()
        setupViewPager(childFragmentManager)
    }

    override fun observeField() {
        super.observeField()
        with(mainViewModel) {
            userInfo.observe(viewLifecycleOwner, Observer {
                viewBinding.userInfo = it
            })
        }
    }

    private fun setupViewPager(fm: FragmentManager?) {
        fm ?: return
        pagerTopics.adapter = topicPagerAdapter
        tabLayoutTopics.setupWithViewPager(pagerTopics)
    }
}
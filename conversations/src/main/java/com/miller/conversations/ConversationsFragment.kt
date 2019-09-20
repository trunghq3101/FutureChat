package com.miller.conversations

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.miller.common.base.BaseFragment
import com.miller.conversations.databinding.ConversationsFragmentMainBinding
import kotlinx.android.synthetic.main.conversations_fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Miller on 19/09/2019
 */

class ConversationsFragment : BaseFragment<ConversationsFragmentMainBinding>() {
    override val layoutId: Int = R.layout.conversations_fragment_main
    override val viewModel: ViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val conversationAdapter = ConversationAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        recyclerConversations.adapter = conversationAdapter
    }

    companion object {
        fun newInstance() = ConversationsFragment()
    }
}
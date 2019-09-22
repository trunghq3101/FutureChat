package com.miller.conversations

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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
    override val viewModel: ConversationsViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val conversationAdapter = ConversationAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeField()
        loadData()
    }

    private fun setupView() {
        recyclerConversations.adapter = conversationAdapter
    }

    private fun loadData() {
        viewModel.fetchConversations()
    }

    private fun observeField() {
        with(viewModel) {
            conversations.observe(viewLifecycleOwner, Observer {
                conversationAdapter.submitList(it)
            })
        }
    }

    companion object {
        fun newInstance() = ConversationsFragment()
    }
}
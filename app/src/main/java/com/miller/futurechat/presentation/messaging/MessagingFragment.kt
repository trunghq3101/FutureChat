package com.miller.futurechat.presentation.messaging

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentMessagingBinding
import com.miller.futurechat.presentation.base.BaseFragment
import com.miller.futurechat.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_messaging.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingFragment : BaseFragment<FragmentMessagingBinding, MessagingViewModel>() {
    override val layoutId: Int = R.layout.fragment_messaging
    override val viewModel: MessagingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val args: MessagingFragmentArgs by navArgs()
    private lateinit var adapter: MessagingAdapter

    override fun initView() {
        super.initView()
        setupToolbar(toolbarMessaging, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAuthState()
    }

    override fun observeField() {
        super.observeField()
        with(viewModel) {
            authState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is AuthState.LoggedIn -> {
                        setupMessagesRecycler()
                        loadMsg(args.conversationId)
                    }
                    is AuthState.LoggedOut -> {
                        showLoggedOutError()
                    }
                }
            })
            pagedList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            networkState.observe(viewLifecycleOwner, Observer {
                adapter.networkState = it
            })
        }
    }

    private fun setupMessagesRecycler() {
        (viewModel.authState.value as? AuthState.LoggedIn)?.token?.let {
            adapter = MessagingAdapter(it)
            recyclerMessages.adapter = adapter
        }
    }

    private fun showLoggedOutError() {}
}
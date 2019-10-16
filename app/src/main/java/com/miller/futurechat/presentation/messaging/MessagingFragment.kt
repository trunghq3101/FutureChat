package com.miller.futurechat.presentation.messaging

import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
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
    private val adapter = MessagingAdapter()

    override fun initView() {
        super.initView()
        viewModel.loadMsg(args.conversationId)
        setupToolbar(toolbarMessaging, false)
    }

    override fun observeField() {
        super.observeField()
        with (viewModel) {
            pagedList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            networkState.observe(viewLifecycleOwner, Observer {
                adapter.networkState = it
            })
        }
    }
}
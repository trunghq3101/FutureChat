package com.miller.futurechat.presentation.messaging

import androidx.navigation.fragment.navArgs
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentMessagingBinding
import com.miller.futurechat.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingFragment : BaseFragment<FragmentMessagingBinding, MessagingViewModel>() {
    override val layoutId: Int = R.layout.fragment_messaging
    override val viewModel: MessagingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val args: MessagingFragmentArgs by navArgs()

    override fun initView() {
        super.initView()
        viewModel.conversationId = args.conversationId
        viewModel.loadMsg()
    }
}
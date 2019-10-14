package com.miller.futurechat.presentation.messaging

import com.miller.common.base.BaseFragment
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentMessagingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingFragment : BaseFragment<FragmentMessagingBinding, MessagingViewModel>() {
    override val layoutId: Int = R.layout.fragment_messaging
    override val viewModel: MessagingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

}
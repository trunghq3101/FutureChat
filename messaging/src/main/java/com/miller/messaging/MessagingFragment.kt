package com.miller.messaging

import com.miller.common.base.BaseFragment
import com.miller.messaging.databinding.MessagingFragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingFragment : BaseFragment<MessagingFragmentMainBinding, MessagingViewModel>() {
    override val layoutId: Int = R.layout.messaging_fragment_main
    override val viewModel: MessagingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel
}
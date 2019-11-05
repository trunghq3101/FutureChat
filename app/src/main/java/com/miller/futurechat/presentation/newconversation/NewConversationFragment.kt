package com.miller.futurechat.presentation.newconversation

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentNewConversationBinding
import com.miller.futurechat.presentation.base.BaseFragment
import com.miller.futurechat.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_new_conversation.*
import org.koin.android.ext.android.inject

class NewConversationFragment : BaseFragment<FragmentNewConversationBinding, NewConversationViewModel>() {
    override val layoutId: Int = R.layout.fragment_new_conversation
    override val viewModel: NewConversationViewModel by inject()
    override val bindingVar: Int = BR.viewModel

    override fun initView() {
        super.initView()
        setupToolbar(toolbarNewConversation)
        editTitle.requestFocus()
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.showSoftInput(editTitle, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
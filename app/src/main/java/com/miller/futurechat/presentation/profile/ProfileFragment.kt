package com.miller.futurechat.presentation.profile

import android.view.View
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentProfileBinding
import com.miller.futurechat.presentation.MainViewModel
import com.miller.futurechat.presentation.base.BaseFragment
import com.miller.futurechat.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Miller on 22/10/2019
 */

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val layoutId: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun initView() {
        super.initView()
        viewBinding.signOutListener = View.OnClickListener {
            mainViewModel.signOut()
        }
        setupToolbar(toolbarProfile, false)
    }
}
package com.miller.futurechat.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.miller.futurechat.utils.NavigationCommand.Back
import com.miller.futurechat.utils.NavigationCommand.To

/**
 * Created by Miller on 20/09/2019
 */

abstract class BaseFragment<ViewBinding: ViewDataBinding, ViewModel: BaseViewModel> : Fragment() {
    @get: LayoutRes
    abstract val layoutId: Int
    abstract val viewModel: ViewModel
    abstract val bindingVar: Int

    protected lateinit var viewBinding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDataBinding(inflater, container)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeField()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @CallSuper
    open fun observeField() {
        with(viewModel) {
            navCommands.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is To -> findNavController().navigate(it.directions)
                    is Back -> {
                        // TODO: Handle back navigation
                    }
                }
            })
        }
    }

    @CallSuper
    open fun initView() {}

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            root.isClickable = true
            setVariable(bindingVar, viewModel)
            executePendingBindings()
        }
    }
}
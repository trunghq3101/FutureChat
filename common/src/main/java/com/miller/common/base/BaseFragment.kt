package com.miller.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * Created by Miller on 20/09/2019
 */

abstract class BaseFragment<ViewBinding: ViewDataBinding> : Fragment() {
    @get: LayoutRes
    abstract val layoutId: Int
    abstract val viewModel: ViewModel
    abstract val bindingVar: Int

    private lateinit var viewBinding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

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
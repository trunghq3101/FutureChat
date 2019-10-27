package com.miller.futurechat.presentation.conversations

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.presentation.MainViewModel
import com.miller.futurechat.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseConversationFragment<ViewBinding : ViewDataBinding, ViewModel : BaseConversationViewModel> :
    BaseFragment<ViewBinding, ViewModel>() {

    abstract val conversationAdapter: BaseConversationAdapter
    abstract val recyclerView: RecyclerView
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val gestureDetector by lazy {
        GestureDetector(context, ConversationGestureListener(recyclerView, viewModel))
    }

    override fun initView() {
        super.initView()
        recyclerView.adapter = conversationAdapter
        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(e)
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })
    }

    override fun observeField() {
        super.observeField()
        with(mainViewModel) {
            authState.observe(viewLifecycleOwner, Observer {
                when (it.peekContent()) {
                    is AuthState.LoggedIn -> viewModel.loadConversations()
                    is AuthState.LoggedOut -> showLoggedOutError()
                }
            })
        }
        with(viewModel) {
            pagedList.observe(viewLifecycleOwner, Observer {
                conversationAdapter.submitList(it)
            })
            networkState.observe(viewLifecycleOwner, Observer {
                conversationAdapter.networkState = it
            })
        }
    }

    private fun showLoggedOutError() {}
}
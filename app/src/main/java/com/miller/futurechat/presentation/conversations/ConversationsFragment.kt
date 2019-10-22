package com.miller.futurechat.presentation.conversations

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentConversationsBinding
import com.miller.futurechat.presentation.MainViewModel
import com.miller.futurechat.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_conversations.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConversationsFragment : BaseFragment<FragmentConversationsBinding, ConversationsViewModel>() {
    override val layoutId: Int = R.layout.fragment_conversations
    override val viewModel: ConversationsViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val conversationAdapter = ConversationAdapter()
    private val gestureDetector by lazy {
        GestureDetector(context, ConversationGestureListener(recyclerConversations, viewModel))
    }

    override fun initView() {
        super.initView()
        recyclerConversations.adapter = conversationAdapter
        recyclerConversations.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
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
            userInfo.observe(viewLifecycleOwner, Observer {
                viewBinding.userInfo = it
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
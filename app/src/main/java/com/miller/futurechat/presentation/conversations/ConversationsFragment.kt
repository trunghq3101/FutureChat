package com.miller.futurechat.presentation.conversations

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.miller.common.base.BaseFragment
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentConversationsBinding
import kotlinx.android.synthetic.main.fragment_conversations.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConversationsFragment : BaseFragment<FragmentConversationsBinding, ConversationsViewModel>() {
    override val layoutId: Int = R.layout.fragment_conversations
    override val viewModel: ConversationsViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val conversationAdapter = ConversationAdapter()
    private val gestureDetector by lazy {
        GestureDetector(context, ConversationGestureListener(recyclerConversations, viewModel))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadConversations()
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
        with(viewModel) {
            conversations.observe(viewLifecycleOwner, Observer {
                conversationAdapter.submitList(it)
            })
        }
    }
}
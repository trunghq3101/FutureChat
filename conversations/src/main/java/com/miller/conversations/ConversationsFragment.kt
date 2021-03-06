package com.miller.conversations

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.miller.common.base.BaseFragment
import com.miller.conversations.databinding.ConversationsFragmentMainBinding
import kotlinx.android.synthetic.main.conversations_fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Miller on 19/09/2019
 */

class ConversationsFragment :
    BaseFragment<ConversationsFragmentMainBinding, ConversationsViewModel>() {
    override val layoutId: Int = R.layout.conversations_fragment_main
    override val viewModel: ConversationsViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val conversationAdapter = ConversationAdapter()
    private val gestureDetector by lazy {
        GestureDetector(context, ConversationGestureListener(recyclerConversations, viewModel))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("----->","ConversationsFragment - onViewCreated : ")
        setupView()
        observeField()
        loadData()
    }

    override fun observeField() {
        super.observeField()
        with(viewModel) {
            conversations.observe(viewLifecycleOwner, Observer {
                conversationAdapter.submitList(it)
            })
        }
    }

    private fun setupView() {
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

    private fun loadData() {
        viewModel.fetchConversations()
    }
}
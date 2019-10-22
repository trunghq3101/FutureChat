package com.miller.futurechat.presentation.messaging

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.FragmentMessagingBinding
import com.miller.futurechat.presentation.base.BaseFragment
import com.miller.futurechat.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_messaging.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingFragment : BaseFragment<FragmentMessagingBinding, MessagingViewModel>() {
    override val layoutId: Int = R.layout.fragment_messaging
    override val viewModel: MessagingViewModel by viewModel()
    override val bindingVar: Int = BR.viewModel

    private val args: MessagingFragmentArgs by navArgs()
    private lateinit var messagingAdapter: MessagingAdapter

    override fun initView() {
        super.initView()
        setupToolbar(toolbarMessaging, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAuthState()
    }

    override fun observeField() {
        super.observeField()
        with(viewModel) {
            authState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is AuthState.LoggedIn -> {
                        setupMessagesRecycler()
                        loadMsg(args.conversationId)
                    }
                    is AuthState.LoggedOut -> {
                        showLoggedOutError()
                    }
                }
            })
            pagedList.observe(viewLifecycleOwner, Observer {
                messagingAdapter.submitList(it)
            })
            networkState.observe(viewLifecycleOwner, Observer {
                messagingAdapter.networkState = it
            })
        }
    }

    private fun setupMessagesRecycler() {
        (viewModel.authState.value as? AuthState.LoggedIn)?.token?.let {

            val obsScrollBottomWhenNewItemAdded =
                object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        super.onItemRangeInserted(positionStart, itemCount)
                        if (positionStart == 0) recyclerMessages.smoothScrollToPosition(
                            positionStart
                        )
                    }
                }
            val obsScrollBottomWhenKeyboardShowed =
                View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                    if (bottom < oldBottom) {
                        recyclerMessages.smoothScrollToPosition(0)
                    }
                }
            val obsTrimWhenListOverSized =
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        when (newState) {
                            RecyclerView.SCROLL_STATE_IDLE -> if (viewModel.pagedList.value?.loadedCount ?: 0 > 40 && (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() == 0) {
                                viewModel.pagedList.value?.dataSource?.invalidate()
                            }
                        }
                    }
                }

            messagingAdapter = MessagingAdapter(it).apply {
                registerAdapterDataObserver(obsScrollBottomWhenNewItemAdded)
            }
            with(recyclerMessages) {
                adapter = messagingAdapter
                layoutManager = LinearLayoutManager(context).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
                addOnLayoutChangeListener(obsScrollBottomWhenKeyboardShowed)
                addOnScrollListener(obsTrimWhenListOverSized)
            }

        }
    }

    private fun showLoggedOutError() {}
}
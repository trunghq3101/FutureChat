package com.miller.futurechat.presentation.conversations

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ConversationGestureListener(
    private val recyclerView: RecyclerView,
    private val viewModel: BaseConversationViewModel
) : GestureDetector.SimpleOnGestureListener() {

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        e?.let { motionEvent ->
            val childView = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)
            childView?.let {
                val position = recyclerView.getChildAdapterPosition(it)
                val conversationId =
                    (recyclerView.adapter as BaseConversationAdapter).getItemAt(position)?.id
                conversationId?.let {
                    viewModel.navigate(ConversationsFragmentDirections.actionConversationsToMessaging(conversationId))
                }
            }
        }
        return super.onSingleTapConfirmed(e)
    }
}
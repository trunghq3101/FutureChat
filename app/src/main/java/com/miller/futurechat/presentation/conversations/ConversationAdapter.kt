package com.miller.futurechat.presentation.conversations

import androidx.recyclerview.widget.DiffUtil
import com.miller.common.base.BaseRecyclerAdapter
import com.miller.futurechat.BR
import com.miller.futurechat.R
import com.miller.futurechat.databinding.ItemConversationBinding
import com.miller.futurechat.presentation.model.ConversationItem

class ConversationAdapter :
    BaseRecyclerAdapter<ConversationItem, ItemConversationBinding>(
        conversationDiff
    ) {
    override val bindingVar: Int = BR.item

    override fun getLayoutRes(viewType: Int): Int = R.layout.item_conversation

}

val conversationDiff = object : DiffUtil.ItemCallback<ConversationItem>() {
    override fun areItemsTheSame(oldItem: ConversationItem, newItem: ConversationItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ConversationItem, newItem: ConversationItem): Boolean {
        return oldItem == newItem
    }

}
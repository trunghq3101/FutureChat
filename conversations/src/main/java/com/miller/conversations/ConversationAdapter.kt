package com.miller.conversations

import androidx.recyclerview.widget.DiffUtil
import com.miller.common.base.BaseRecyclerAdapter
import com.miller.conversations.databinding.ConversationsItemConversationBinding
import com.miller.conversations.model.ConversationItem

/**
 * Created by Miller on 20/09/2019
 */

class ConversationAdapter :
    BaseRecyclerAdapter<ConversationItem, ConversationsItemConversationBinding>(
        conversationDiff
    ) {
    override val bindingVar: Int = BR.item

    override fun getLayoutRes(viewType: Int): Int = R.layout.conversations_item_conversation

}

val conversationDiff = object : DiffUtil.ItemCallback<ConversationItem>() {
    override fun areItemsTheSame(oldItem: ConversationItem, newItem: ConversationItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ConversationItem, newItem: ConversationItem): Boolean {
        return oldItem == newItem
    }

}
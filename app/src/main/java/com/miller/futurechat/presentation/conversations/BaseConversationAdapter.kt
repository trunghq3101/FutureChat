package com.miller.futurechat.presentation.conversations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.miller.core.domain.model.Conversation
import com.miller.futurechat.R
import com.miller.futurechat.databinding.ItemConversationBinding
import com.miller.futurechat.databinding.PagingItemStateBinding
import com.miller.futurechat.presentation.model.mapToPresentation
import com.miller.paging.PagingAdapter

open class BaseConversationAdapter : PagingAdapter<Conversation>(conversationDiff) {
    override val adapterCallback: AdapterListUpdateCallback = AdapterListUpdateCallback(this)
    override val listUpdateCallback: ListUpdateCallback = WithNetworkStateListUpdateCallback(adapterCallback)

    override fun createNetworkStateViewHolder(parent: ViewGroup): NetworkStateItemViewHolder {
        return DataBindingUtil.inflate<PagingItemStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.paging_item_state,
            parent,
            false
        ).let {
            ConversationNetworkStateVH(it)
        }
    }

    override fun createDataItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<Conversation> {
        return DataBindingUtil.inflate<ItemConversationBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_conversation,
            parent,
            false
        ).let {
            ConversationVH(it)
        }
    }

    override fun getDataItemViewType(position: Int): Int {
        return TYPE_DEFAULT
    }

    fun getItemAt(position: Int) = getItem(position)

    class ConversationVH(
        private val binding: ItemConversationBinding
    ) : ItemViewHolder<Conversation>(binding.root) {
        override fun bind(item: Conversation?, position: Int) {
            binding.item = item?.mapToPresentation()
        }

    }

    class ConversationNetworkStateVH(
        private val binding: PagingItemStateBinding
    ) : NetworkStateItemViewHolder(binding.root) {
        override fun bindProgressBar(showProgress: Boolean) {
            binding.showProgressBar = showProgress
        }

        override fun bindError(showError: Boolean, msgError: String?) {
            binding.showError = showError
            binding.msgError = msgError
        }
    }

    companion object {
        const val TYPE_DEFAULT = 1
    }
}

val conversationDiff = object : DiffUtil.ItemCallback<Conversation>() {
    override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
        return oldItem == newItem
    }

}
package com.miller.futurechat.presentation.messaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import com.miller.futurechat.R
import com.miller.futurechat.databinding.ItemMessageMeBinding
import com.miller.futurechat.databinding.ItemMessageOtherBinding
import com.miller.futurechat.databinding.PagingItemStateBinding
import com.miller.futurechat.presentation.model.MessageItem
import com.miller.futurechat.presentation.model.type.OwnerType
import com.miller.paging.PagingAdapter

/**
 * Created by Miller on 16/10/2019
 */

class MessagingAdapter : PagingAdapter<MessageItem>(messagingDiffUtil) {
    override val adapterCallback: AdapterListUpdateCallback = AdapterListUpdateCallback(this)

    override fun createNetworkStateViewHolder(parent: ViewGroup): NetworkStateItemViewHolder {
        return DataBindingUtil.inflate<PagingItemStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.paging_item_state,
            parent,
            false
        ).let {
            MessagingNetworkStateVH(it)
        }
    }

    override fun createDataItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<MessageItem> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ME -> DataBindingUtil.inflate<ItemMessageMeBinding>(
                inflater,
                R.layout.item_message_me,
                parent,
                false
            ).let { MessageMeVH(it) }
            TYPE_OTHER -> DataBindingUtil.inflate<ItemMessageOtherBinding>(
                inflater,
                R.layout.item_message_other,
                parent,
                false
            ).let { MessageOtherVH(it) }
            else -> DataBindingUtil.inflate<ItemMessageMeBinding>(
                inflater,
                R.layout.item_message_me,
                parent,
                false
            ).let { MessageMeVH(it) }
        }
    }

    override fun getDataItemViewType(position: Int): Int {
        return when (getItem(position)?.ownerType) {
            OwnerType.Me -> TYPE_ME
            OwnerType.Other -> TYPE_OTHER
            null -> TYPE_ME
        }
    }

    class MessageMeVH(
        private val binding: ItemMessageMeBinding
    ) : ItemViewHolder<MessageItem>(binding.root) {
        override fun bind(item: MessageItem?) {
            binding.item = item
        }
    }

    class MessageOtherVH(
        private val binding: ItemMessageOtherBinding
    ) : ItemViewHolder<MessageItem>(binding.root) {
        override fun bind(item: MessageItem?) {
            binding.item = item
        }
    }

    class MessagingNetworkStateVH(
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
        const val TYPE_ME = 1
        const val TYPE_OTHER = 2

        val messagingDiffUtil = object : DiffUtil.ItemCallback<MessageItem>() {
            override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}
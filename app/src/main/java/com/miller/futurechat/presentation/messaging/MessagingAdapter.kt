package com.miller.futurechat.presentation.messaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.miller.core.domain.model.Message
import com.miller.futurechat.R
import com.miller.futurechat.databinding.ItemMessageMeBinding
import com.miller.futurechat.databinding.ItemMessageOtherBinding
import com.miller.futurechat.databinding.PagingItemStateBinding
import com.miller.futurechat.presentation.model.mapToPresentation
import com.miller.paging.PagingAdapter

/**
 * Created by Miller on 16/10/2019
 */

class MessagingAdapter(
    private val userId: String
) : PagingAdapter<Message>(messagingDiffUtil) {

    override val adapterCallback: AdapterListUpdateCallback = AdapterListUpdateCallback(this)
    override val listUpdateCallback: ListUpdateCallback = object : WithNetworkStateListUpdateCallback(adapterCallback) {
        override fun onInserted(position: Int, count: Int) {
            super.onInserted(position, count)
            updateRelativePositions(position)
        }

        private fun updateRelativePositions(position: Int) {
            if (position > 0) notifyItemRangeChanged(position - 1, position)
        }
    }

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
    ): ItemViewHolder<Message> {
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
        return when (getItem(position)?.senderId == userId) {
            true -> TYPE_ME
            else -> TYPE_OTHER
        }
    }

    inner class MessageMeVH(
        private val binding: ItemMessageMeBinding
    ) : ItemViewHolder<Message>(binding.root) {
        override fun bind(item: Message?, position: Int) {
            binding.item = item?.mapToPresentation(
                userId,
                getItem(position - 1),
                getItem(position + 1)
            )
        }
    }

    inner class MessageOtherVH(
        private val binding: ItemMessageOtherBinding
    ) : ItemViewHolder<Message>(binding.root) {
        override fun bind(item: Message?, position: Int) {
            binding.item = item?.mapToPresentation(
                userId,
                getItem(position - 1),
                getItem(position + 1)
            )
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

        val messagingDiffUtil = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Message,
                newItem: Message
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
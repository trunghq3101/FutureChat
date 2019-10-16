package com.miller.futurechat.presentation.messaging

import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import com.miller.futurechat.framework.model.MessageEntity
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.presentation.model.mapToPresentation
import com.miller.futurechat.presentation.model.type.OwnerType
import com.miller.paging.PagingAdapter

/**
 * Created by Miller on 16/10/2019
 */

class MessagingAdapter : PagingAdapter<MessageEntity>(messagingDiffUtil) {
    override val adapterCallback: AdapterListUpdateCallback = AdapterListUpdateCallback(this)

    override fun createNetworkStateViewHolder(parent: ViewGroup): NetworkStateItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createDataItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<MessageEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataItemViewType(position: Int): Int {
        return when (getItem(position)?.mapToDomain()?.mapToPresentation()?.ownerType) {
            OwnerType.Me -> TYPE_ME
            OwnerType.Other -> TYPE_OTHER
            null -> TYPE_ME
        }
    }

    companion object {
        const val TYPE_ME = 1
        const val TYPE_OTHER = 2

        val messagingDiffUtil = object : DiffUtil.ItemCallback<MessageEntity>() {
            override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}
package com.miller.paging

import android.view.View
import android.view.ViewGroup
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.*

/**
 * Created by Miller on 16/10/2019
 */

abstract class PagingAdapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>
) : PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback) {

    abstract val adapterCallback: AdapterListUpdateCallback

    var networkState: PagingNetworkState? = null
        set(value) {
            val prev = field
            val prevExtraRow = hasExtraRow()
            field = value
            val newExtraRow = hasExtraRow()
            if (prevExtraRow != newExtraRow) {
                if (prevExtraRow) {
                    notifyItemRemoved(differ.itemCount)
                } else {
                    notifyItemInserted(differ.itemCount)
                }
            } else if (newExtraRow && prev != value) {
                notifyItemChanged(itemCount - 1)
            }
        }

    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapterCallback.onChanged(position + 1, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapterCallback.onMoved(fromPosition + 1, toPosition + 1)
        }

        override fun onInserted(position: Int, count: Int) {
            adapterCallback.onInserted(position + 1, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapterCallback.onRemoved(position + 1, count)
        }
    }

    private val differ = AsyncPagedListDiffer<Item>(listUpdateCallback,
        AsyncDifferConfig.Builder<Item>(diffCallback).build())

    abstract fun createNetworkStateViewHolder(parent: ViewGroup): NetworkStateItemViewHolder

    abstract fun createDataItemViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<Item>

    abstract fun getDataItemViewType(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PROGRESS -> createNetworkStateViewHolder(parent)
            else -> createDataItemViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder<*>) {
            (holder as ItemViewHolder<Item>).bind(getItem(position))
        } else if (holder is NetworkStateItemViewHolder) {
            holder.bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            getDataItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return differ.itemCount + if (hasExtraRow()) 1 else 0
    }

    override fun getItem(position: Int): Item? {
        return differ.getItem(position)
    }

    override fun submitList(pagedList: PagedList<Item>?) {
        differ.submitList(pagedList)
    }

    override fun getCurrentList(): PagedList<Item>? {
        return differ.currentList
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && this.networkState !== PagingNetworkState.LOADED
    }

    abstract class ItemViewHolder<Item>(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Item?)
    }

    abstract class NetworkStateItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        abstract fun bindProgressBar(showProgress: Boolean)

        abstract fun bindError(showError: Boolean, msgError: String?)

        fun bind(networkState: PagingNetworkState?) {
            var showProgressBar = false
            var showError = false
            if (networkState != null && networkState.status == PagingNetworkState.Status.RUNNING) {
                showProgressBar = true
            }
            bindProgressBar(showProgressBar)
            if (networkState != null && networkState.status == PagingNetworkState.Status.FAILED) {
                showError = true
            }
            bindError(showError, networkState?.msg)
        }

    }

    companion object {
        const val TYPE_PROGRESS = 0
    }
}
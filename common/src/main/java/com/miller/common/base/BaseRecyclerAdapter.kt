package com.miller.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

/**
 * Created by Miller on 20/09/2019
 */

abstract class BaseRecyclerAdapter<Item, ViewBinding : ViewDataBinding>(
    callBack: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseViewHolder<ViewBinding>>(
    AsyncDifferConfig.Builder<Item>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    abstract val bindingVar: Int

    override fun submitList(list: List<Item>?) {
        super.submitList(ArrayList<Item>(list ?: listOf()))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                getLayoutRes(viewType),
                parent, false
            ).apply {
                bindFirstTime(this)
            })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        try {
            val item: Item = getItem(position)
            holder.binding.setVariable(bindingVar, item)
            bindView(holder.binding, item, position)
        } catch (e: IndexOutOfBoundsException) {
            bind(holder.binding, position)
        }
        holder.binding.executePendingBindings()
    }

    fun getItemAt(position: Int): Item = super.getItem(position)

    /**
     * get layout res based on view type
     */
    protected abstract fun getLayoutRes(viewType: Int): Int

    /**
     * override if need
     * bind first time
     * use for set item onClickListener, something only set one time
     */
    protected open fun bindFirstTime(binding: ViewBinding) {}

    /**
     * override if need
     * bind view
     */
    protected open fun bindView(binding: ViewBinding, item: Item, position: Int) {}

    protected open fun bind(binding: ViewBinding, position: Int) {}
}

open class BaseViewHolder<ViewBinding : ViewDataBinding> constructor(
    val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root)
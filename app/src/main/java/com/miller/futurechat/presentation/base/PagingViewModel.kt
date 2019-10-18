package com.miller.futurechat.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.miller.core.usecases.UseCases
import com.miller.futurechat.utils.SingleLiveEvent
import com.miller.paging.LivePagingWrapper
import com.miller.paging.PagingDataLoader
import org.koin.core.inject

/**
 * Created by Miller on 17/10/2019
 */

abstract class PagingViewModel<Item> : BaseViewModel() {

    abstract val pagingDataLoader: PagingDataLoader<Item>
    override val useCases: UseCases by inject()

    protected val pagingWrapper = SingleLiveEvent<LivePagingWrapper<Item>>()

    val pagedList: LiveData<PagedList<Item>> = Transformations.switchMap(pagingWrapper) {
        it.livePagedList
    }
    val networkState = Transformations.switchMap(pagingWrapper) {
        it.liveNetworkState
    }
}
package com.miller.paging

import androidx.paging.Config
import androidx.paging.toLiveData
import com.miller.paging.PagingBoundaryCallback.Companion.PAGE_SIZE
import com.miller.paging.PagingConstants.MAX_PAGES_IN_MEMORY
import java.util.concurrent.Executors

/**
 * Created by Miller on 16/10/2019
 */

fun <Item> PagingDataLoader<Item>.fetchPage(): LivePagingWrapper<Item> {
    val boundaryCallback = PagingBoundaryCallback(Executors.newSingleThreadExecutor(), this)
    return LivePagingWrapper(
        livePagedList = fetchPageFromLocal().toLiveData(
            config = Config(pageSize = PAGE_SIZE, maxSize = MAX_PAGES_IN_MEMORY, enablePlaceholders = false),
            boundaryCallback = boundaryCallback
        ),
        liveNetworkState = boundaryCallback.liveNetworkState
    )
}
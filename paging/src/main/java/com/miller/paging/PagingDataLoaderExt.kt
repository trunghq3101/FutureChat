package com.miller.paging

import androidx.paging.toLiveData
import java.util.concurrent.Executors

/**
 * Created by Miller on 16/10/2019
 */

fun <Item> PagingDataLoader<Item>.fetchPage(): LivePagingWrapper<Item> {
    val boundaryCallback = PagingBoundaryCallback(Executors.newSingleThreadExecutor(), this)
    return LivePagingWrapper(
        livePagedList = fetchPageFromLocal().toLiveData(
            pageSize = PagingBoundaryCallback.PAGE_SIZE,
            boundaryCallback = boundaryCallback
        ),
        liveNetworkState = boundaryCallback.liveNetworkState
    )
}
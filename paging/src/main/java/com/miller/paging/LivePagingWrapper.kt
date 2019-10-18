package com.miller.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Created by Miller on 16/10/2019
 */

data class LivePagingWrapper<T>(
    val livePagedList: LiveData<PagedList<T>>,
    val liveNetworkState: LiveData<PagingNetworkState>
)
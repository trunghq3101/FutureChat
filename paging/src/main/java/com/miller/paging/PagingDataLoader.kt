package com.miller.paging

import androidx.paging.DataSource
import io.reactivex.Single

/**
 * Created by Miller on 16/10/2019
 */

interface PagingDataLoader<Item> {

    fun fetchPageFromLocal(): DataSource.Factory<Int, Item>

    fun fetchBefore(firstItem: Item): Single<List<Item>>

    fun fetchAfter(lastItem: Item?): Single<List<Item>>

    fun savePageToLocal(items: List<Item>): Single<List<Long>>

    fun clearPageInLocal(): Single<Void>
}
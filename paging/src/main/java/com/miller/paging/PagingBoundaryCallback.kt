package com.miller.paging

import androidx.paging.PagedList
import com.miller.paging.PagingRequestHelper.RequestType.AFTER
import com.miller.paging.PagingRequestHelper.RequestType.INITIAL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

/**
 * Created by Miller on 16/10/2019
 */

class PagingBoundaryCallback<Item>(
    private val executor: Executor,
    private val pagingRepository: PagingDataLoader<Item>
): PagedList.BoundaryCallback<Item>() {

    private val helper = PagingRequestHelper(executor)
    val compositeDisposable = CompositeDisposable()
    val liveNetworkState = helper.createStatusLiveData()

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(INITIAL, object : PagingRequestHelper.Request {
            override fun run(callback: PagingRequestHelper.Request.Callback) {
                pagingRepository.fetchPageFromRemote(null)
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            insertItemsIntoDb(it, callback)
                        },
                        {
                            callback.recordFailure(it)
                        }
                    )
                    .apply {
                        compositeDisposable.add(this)
                    }
            }
        })
    }

    override fun onItemAtEndLoaded(itemAtEnd: Item) {
        helper.runIfNotRunning(AFTER, object : PagingRequestHelper.Request {
            override fun run(callback: PagingRequestHelper.Request.Callback) {
                pagingRepository.fetchPageFromRemote(itemAtEnd)
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            insertItemsIntoDb(it, callback)
                        },
                        {
                            callback.recordFailure(it)
                        }
                    )
                    .apply {
                        compositeDisposable.add(this)
                    }
            }
        })
    }

    private fun insertItemsIntoDb(
        items: List<Item>,
        callback: PagingRequestHelper.Request.Callback
    ) {
        pagingRepository.savePageToLocal(items)
            .subscribeOn(Schedulers.from(executor))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback.recordSuccess()
                },
                {
                    callback.recordFailure(it)
                }
            )
            .apply {
                compositeDisposable.add(this)
            }
    }

    companion object{
        // This number is fixed in specification
        const val PAGE_SIZE = 12
    }
}
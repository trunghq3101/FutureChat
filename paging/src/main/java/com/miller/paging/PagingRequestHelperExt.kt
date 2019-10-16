package com.miller.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by Miller on 16/10/2019
 */


private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
    return PagingRequestHelper.RequestType.values().map {
        // TODO: Change error message
        report.getErrorFor(it)?.message ?: "Something wrong"
    }.first()
}

fun PagingRequestHelper.createStatusLiveData(): LiveData<PagingNetworkState> {
    val liveData = MutableLiveData<PagingNetworkState>()
    addListener(object: PagingRequestHelper.Listener {
        override fun onStatusChange(report: PagingRequestHelper.StatusReport) {
            when {
                report.hasRunning() -> liveData.postValue(PagingNetworkState.LOADING)
                report.hasError() -> liveData.postValue(
                    PagingNetworkState.error(
                        getErrorMessage(
                            report
                        )
                    )
                )
                else -> liveData.postValue(PagingNetworkState.LOADED)
            }
        }
    })
    return liveData
}
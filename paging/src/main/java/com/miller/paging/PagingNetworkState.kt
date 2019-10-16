package com.miller.paging

/**
 * Created by Miller on 16/10/2019
 */

class PagingNetworkState(val status: Status, val msg: String?) {

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    companion object {

        val LOADED: PagingNetworkState =
            PagingNetworkState(
                Status.SUCCESS,
                "Success"
            )
        val LOADING: PagingNetworkState =
            PagingNetworkState(
                Status.RUNNING,
                "Running"
            )
        fun error(msg: String?) = PagingNetworkState(
            Status.FAILED,
            msg
        )

    }
}
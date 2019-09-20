package com.miller.common.utils

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Miller on 20/09/2019
 */

object SchedulersUtils {

    fun <T> applyAsyncSchedulersSingle(): SingleTransformer<T, T> {
        return SingleTransformer { func ->
            func.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}

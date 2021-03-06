package com.miller.futurechat.utils

import io.reactivex.CompletableTransformer
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

    fun applyAsyncSchedulersCompletable(): CompletableTransformer {
        return CompletableTransformer { func ->
            func.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}

package com.miller.utils

import com.google.android.gms.tasks.Task
import io.reactivex.Single

fun <T> Task<T>.toSingle(): Single<T> {
    return Single.create { emitter ->
        this.addOnSuccessListener {
            emitter.onSuccess(it)
        }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }
}
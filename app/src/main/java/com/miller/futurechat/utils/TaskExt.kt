package com.miller.futurechat.utils

import com.google.android.gms.tasks.Task
import io.reactivex.Completable
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

fun <T> Task<T>.toCompletable(): Completable {
    return Completable.create { emitter ->
        this.addOnSuccessListener {
            emitter.onComplete()
        }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }
}
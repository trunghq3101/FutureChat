package com.miller.futurechat.framework.datasourceimpl

import com.miller.core.data.datasource.LocalAuthDataSource
import com.miller.futurechat.framework.sharedPref.PrefHelper
import io.reactivex.Single

class PrefAuthenticationDataSource(
    private val prefHelper: PrefHelper
): LocalAuthDataSource {

    override fun readToken(): Single<String> {
        return Single.create { emitter ->
            prefHelper.readToken()?.let {
                if (it.isNotEmpty()) emitter.onSuccess(it) else emitter.onError(Throwable())
            } ?: run {
                emitter.onError(Throwable())
            }
        }
    }

    override fun updateToken(token: String): Single<String> {
        return Single.create { emitter ->
            prefHelper.setToken(token)
            emitter.onSuccess(token)
        }
    }

}
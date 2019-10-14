package com.miller.core.data.datasource

import io.reactivex.Single

interface AuthenticationDataSource {
    fun readToken(): Single<String>
    fun updateToken(token: String): Single<String>
}
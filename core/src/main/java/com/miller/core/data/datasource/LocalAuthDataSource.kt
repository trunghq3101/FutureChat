package com.miller.core.data.datasource

import io.reactivex.Single

interface LocalAuthDataSource {
    fun readToken(): Single<String>
    fun updateToken(token: String): Single<String>
    fun deleteToken(): Single<Boolean>
}
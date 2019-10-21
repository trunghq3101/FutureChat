package com.miller.core.data.repository

import com.miller.core.data.datasource.LocalAuthDataSource
import com.miller.core.data.datasource.RemoteAuthDataSource

class AuthenticationRepository(
    private val localDataSource: LocalAuthDataSource,
    private val remoteDataSource: RemoteAuthDataSource
) {
    fun getToken() = localDataSource.readToken()
    fun updateToken(token: String) = localDataSource.updateToken(token)
    fun getUserInfo(token: String) = remoteDataSource.getUserInfo(token)
}
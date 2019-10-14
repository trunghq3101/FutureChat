package com.miller.core.data.repository

import com.miller.core.data.datasource.AuthenticationDataSource

class AuthenticationRepository(
    private val dataSource: AuthenticationDataSource
) {
    fun getToken() = dataSource.readToken()
    fun updateToken(token: String) = dataSource.updateToken(token)
}
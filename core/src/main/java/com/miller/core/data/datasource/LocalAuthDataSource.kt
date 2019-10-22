package com.miller.core.data.datasource

interface LocalAuthDataSource {
    fun readToken(): String
}
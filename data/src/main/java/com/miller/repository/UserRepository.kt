package com.miller.repository

import com.miller.datasource.sharePref.SharedPrefDataSource

/**
 * Created by Miller on 19/09/2019
 */

class UserRepository(
    private val sharedPrefDataSource: SharedPrefDataSource
) {

    fun saveUserId(uid: String) {
        sharedPrefDataSource.saveUserId(uid)
    }

    fun readUserId() = sharedPrefDataSource.readUserId()
}
package com.miller.datasource.sharePref

/**
 * Created by Miller on 19/09/2019
 */

class SharedPrefDataSource(
    private val sharedPrefApi: SharedPrefApi
) {

    fun saveUserId(uid: String) = sharedPrefApi.set(KEY_USER_ID, uid)

    fun readUserId(): String = sharedPrefApi.get(KEY_USER_ID, "")

    companion object {
        const val KEY_USER_ID = "KEY_USER_ID"
    }
}
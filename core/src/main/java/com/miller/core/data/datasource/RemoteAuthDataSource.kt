package com.miller.core.data.datasource

import com.miller.core.domain.model.User
import io.reactivex.Single

/**
 * Created by Miller on 21/10/2019
 */

interface RemoteAuthDataSource {
    fun getUserInfo(token: String): Single<User>
}
package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.miller.core.data.datasource.RemoteAuthDataSource
import com.miller.core.domain.model.User
import com.miller.futurechat.framework.firestore.CollectionsConstant.USERS
import com.miller.futurechat.framework.model.UserEntity
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.utils.toSingle
import io.reactivex.Single

/**
 * Created by Miller on 21/10/2019
 */

class FirestoreAuthDataSource(
    private val firestore: FirebaseFirestore
) : RemoteAuthDataSource {

    override fun getUserInfo(token: String): Single<User> {
        return queryUser(token)
            .get()
            .toSingle()
            .map { it.toObject(UserEntity::class.java)?.apply {
                avatarUrl = "https://picsum.photos/id/237/200/300"
            }?.mapToDomain() }
    }

    private fun queryUser(token: String) = firestore.collection(USERS).document(token)
}
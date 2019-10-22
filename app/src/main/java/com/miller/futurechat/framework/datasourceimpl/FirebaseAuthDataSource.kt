package com.miller.futurechat.framework.datasourceimpl

import com.google.firebase.auth.FirebaseAuth
import com.miller.core.data.datasource.LocalAuthDataSource

class FirebaseAuthDataSource(
    private val firebaseAuth: FirebaseAuth
) : LocalAuthDataSource {

    override fun readToken(): String {
        return firebaseAuth.currentUser?.uid ?: UNKNOWN_USER_ID
    }

    companion object {
        const val UNKNOWN_USER_ID = "UNKNOWN_USER_ID"
    }
}
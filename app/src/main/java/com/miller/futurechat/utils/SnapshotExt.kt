package com.miller.futurechat.utils

import com.google.firebase.firestore.QuerySnapshot

fun <T: Any> QuerySnapshot.toItemList(type: Class<T>): List<T> {
    return this.documents.mapNotNull {
        it.toObject(type)
    }
}
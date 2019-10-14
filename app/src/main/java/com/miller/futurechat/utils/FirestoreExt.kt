package com.miller.futurechat.utils

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Miller on 19/09/2019
 */

fun <T : Any> FirebaseFirestore.createNew(colPath: String, data: T) =
    this.collection(colPath).add(data)

fun <T : Any> FirebaseFirestore.updateArray(
    docPath: String,
    field: String,
    value: T
) = this.document(docPath).update(
    field,
    FieldValue.arrayUnion(value)
)

fun <T> FirebaseFirestore.getAll(
    colPath: String,
    field: String,
    values: List<T>
): Task<List<DocumentSnapshot>> {
    return values.map { this.collection(colPath).whereEqualTo(field, it).get() }.let {
        Tasks.whenAllSuccess<DocumentSnapshot>(it)
    }
}
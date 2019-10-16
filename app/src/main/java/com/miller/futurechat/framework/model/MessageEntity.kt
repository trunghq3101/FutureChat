package com.miller.futurechat.framework.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

/**
 * Created by Miller on 16/10/2019
 */

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    @DocumentId
    val id: String,
    val contentText: String,
    val senderId: String
)
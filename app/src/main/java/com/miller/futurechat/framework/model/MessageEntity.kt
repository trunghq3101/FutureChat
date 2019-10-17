package com.miller.futurechat.framework.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

/**
 * Created by Miller on 16/10/2019
 */

@Entity(tableName = "messages", foreignKeys = [ForeignKey(
    entity = ConversationEntity::class,
    parentColumns = ["id"],
    childColumns = ["conversationId"]
)])
data class MessageEntity(
    @DocumentId
    @PrimaryKey
    val id: String,
    val contentText: String,
    val senderId: String,
    val conversationId: String
) {
    constructor() : this(id = "", contentText = "", senderId = "", conversationId = "")
}
package com.miller.futurechat.framework.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.util.*

/**
 * Created by Miller on 16/10/2019
 */

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = ConversationEntity::class,
        parentColumns = ["id"],
        childColumns = ["conversationId"]
    )],
    indices = [Index(value = ["id", "conversationId"])]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val contentText: String,
    val senderId: String,
    val conversationId: String,
    val timestamp: Long
) {
    constructor() : this(
        id = 0,
        contentText = "",
        senderId = "",
        conversationId = "",
        timestamp = Date().time
    )
}
package com.miller.futurechat.framework.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

/**
 * Created by Miller on 15/10/2019
 */

@Entity(tableName = "conversations")
data class ConversationEntity(
    @DocumentId
    @PrimaryKey
    val id: String,
    val avatarUrl: String,
    val followers: List<String>,
    val title: String,
    val lastMessage: String
) {
    constructor() : this("", "", listOf(), "", "")
}
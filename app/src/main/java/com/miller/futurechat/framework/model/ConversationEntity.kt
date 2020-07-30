package com.miller.futurechat.framework.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.miller.futurechat.framework.constants.NEWEST_TOPIC_TYPE
import com.miller.futurechat.framework.constants.TopicTypeDef

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
    val lastMessage: String,
    @TopicTypeDef
    var topicType: Int = NEWEST_TOPIC_TYPE
) {
    constructor() : this("", "", listOf(), "", "", NEWEST_TOPIC_TYPE)
}
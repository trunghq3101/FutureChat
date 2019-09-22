package com.miller.model

import com.google.firebase.firestore.DocumentId
import com.miller.conversations.model.ConversationItem

data class ConversationEntity(
    @DocumentId
    val id: String? = null,
    val avatarUrl: String? = null,
    val title: String? = null,
    val lastMessage: String? = null
) {
    fun toItem(): ConversationItem = ConversationItem(
        id = id ?: "",
        avatar = avatarUrl ?: "",
        title = title ?: "",
        content = lastMessage ?: ""
    )
}
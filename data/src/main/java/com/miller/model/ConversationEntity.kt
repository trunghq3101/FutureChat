package com.miller.model

import com.miller.conversations.model.ConversationItem

data class ConversationEntity(
    val avatarUrl: String? = null,
    val title: String? = null,
    val lastMessage: String? = null
) {
    fun toItem(): ConversationItem = ConversationItem(
        avatar = avatarUrl ?: "",
        title = title ?: "",
        content = lastMessage ?: ""
    )
}
package com.miller.futurechat.presentation.model

import com.miller.core.domain.model.Conversation

fun Conversation.mapToPresentation(): ConversationItem {
    return ConversationItem(
        id = this.id,
        avatar = this.avatarUrl,
        title = this.title,
        content = this.lastMessage
    )
}
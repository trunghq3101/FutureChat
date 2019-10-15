package com.miller.futurechat.presentation.model

import com.miller.core.domain.model.Conversation
import com.miller.core.domain.model.Message

fun Conversation.mapToPresentation(): ConversationItem {
    return ConversationItem(
        id = this.id,
        avatar = this.avatarUrl,
        title = this.title,
        content = this.lastMessage
    )
}

fun Message.mapToPresentation(): MessageItem {
    return MessageItem(
        id = this.id,
        contentText = this.contentText
    )
}
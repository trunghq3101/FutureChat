package com.miller.futurechat.presentation.model

import com.miller.core.domain.model.Conversation
import com.miller.core.domain.model.Message
import com.miller.futurechat.presentation.model.type.OwnerType
import com.miller.futurechat.presentation.model.type.RelativePositionType.*

fun Conversation.mapToPresentation(): ConversationItem {
    return ConversationItem(
        id = this.id,
        avatar = this.avatarUrl,
        title = this.title,
        content = this.lastMessage
    )
}

fun Message.mapToPresentation(
    userId: String,
    msgBefore: Message?,
    msgAfter: Message?
): MessageItem {
    val position = if (msgBefore?.senderId == msgAfter?.senderId) {
        if (null == msgBefore) Single else Mid
    } else {
        when (senderId) {
            msgBefore?.senderId -> Bot
            msgAfter?.senderId -> Top
            else -> Single
        }
    }
    return MessageItem(
        id = this.id,
        contentText = this.contentText,
        ownerType = if (senderId == userId) OwnerType.Me else OwnerType.Other,
        relativePosition = position
    )
}
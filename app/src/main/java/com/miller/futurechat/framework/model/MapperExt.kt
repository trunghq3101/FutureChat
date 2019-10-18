package com.miller.futurechat.framework.model

import com.google.firebase.Timestamp
import com.miller.core.domain.model.Conversation
import com.miller.core.domain.model.Message

/**
 * Created by Miller on 15/10/2019
 */

fun ConversationEntity.mapToDomain(): Conversation {
    return Conversation(id, avatarUrl, followers, title, lastMessage)
}

fun MessageEntity.mapToDomain(): Message {
    return Message(id, contentText, senderId, conversationId, timestamp.toDate())
}

fun Message.mapToFramework(): MessageEntity {
    return MessageEntity(id, contentText, senderId, conversationId, Timestamp(timestamp))
}

fun Conversation.mapToFramework(): ConversationEntity {
    return ConversationEntity(id, avatarUrl, followers, title, lastMessage)
}
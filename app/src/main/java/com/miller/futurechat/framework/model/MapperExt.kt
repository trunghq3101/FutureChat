package com.miller.futurechat.framework.model

import com.miller.core.domain.model.Conversation
import com.miller.core.domain.model.Message
import com.miller.core.domain.model.User

/**
 * Created by Miller on 15/10/2019
 */

fun ConversationEntity.mapToDomain(): Conversation {
    return Conversation(id, avatarUrl, followers, title, lastMessage)
}

fun MessageEntity.mapToDomain(): Message {
    return Message(id, contentText, senderId, conversationId, timestamp)
}

fun UserEntity.mapToDomain(): User {
    return User(id, avatarUrl)
}

fun Message.mapToFramework(): MessageEntity {
    return MessageEntity(id, contentText, senderId, conversationId, timestamp)
}

fun Conversation.mapToFramework(): ConversationEntity {
    return ConversationEntity(id, avatarUrl, followers, title, lastMessage)
}
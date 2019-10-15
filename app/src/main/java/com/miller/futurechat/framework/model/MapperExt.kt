package com.miller.futurechat.framework.model

import com.miller.core.domain.model.Conversation

/**
 * Created by Miller on 15/10/2019
 */

fun ConversationEntity.mapToDomain(): Conversation {
    return Conversation(id, avatarUrl, followers, title, lastMessage)
}
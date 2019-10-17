package com.miller.core.domain.model

data class Message(
    val id: String,
    val contentText: String,
    val senderId: String,
    val conversationId: String
)
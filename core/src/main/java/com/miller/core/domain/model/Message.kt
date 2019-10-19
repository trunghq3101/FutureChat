package com.miller.core.domain.model

data class Message(
    var id: Int = 0,
    val contentText: String,
    val senderId: String,
    val conversationId: String,
    val timestamp: Long
)
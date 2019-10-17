package com.miller.core.domain.model

import java.util.*

data class Message(
    val id: String,
    val contentText: String,
    val senderId: String,
    val conversationId: String,
    val timestamp: Date
)
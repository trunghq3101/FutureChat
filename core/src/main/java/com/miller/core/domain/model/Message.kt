package com.miller.core.domain.model

data class Message(
    var id: Int = 0,
    val contentText: String,
    val senderId: String,
    val conversationId: String,
    val timestamp: Long
) {
    companion object {
        const val KEY_ID = "id"
        const val KEY_CONTENT_TEXT = "contentText"
        const val KEY_SENDER_ID = "senderId"
        const val KEY_CONVERSATION_ID = "conversationId"
        const val KEY_TIMESTAMP = "timestamp"
    }
}
package com.miller.futurechat.framework.firestore

object CollectionsConstant {
    const val NOTIFICATION_TOKENS = "notificationTokens"
    const val CONVERSATIONS = "conversations"
    const val MESSAGES = "messages"
    object NotificationTokensConstant {
        const val TOKENS = "tokens"
        const val USER_ID = "userId"
    }
    object ConversationsConstant {
        const val FOLLOWERS = "followers"
    }
}
package com.miller.futurechat.framework.firestore

object CollectionsConstant {
    const val NOTIFICATION_TOKENS = "notificationTokens"
    const val CONVERSATIONS = "conversations"
    const val MESSAGES = "messages"
    const val USERS = "users"
    object NotificationTokensConstant {
        const val TOKENS = "tokens"
        const val USER_ID = "userId"
    }
    object ConversationsConstant {
        const val AVATAR_URL = "avatarUrl"
        const val FOLLOWERS = "followers"
        const val LAST_MESSAGE = "lastMessage"
        const val TITLE = "title"
    }
    object MessagesConstant {
        const val TIMESTAMP = "timestamp"
    }
}
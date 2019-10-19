package com.miller.core.usecases

data class UseCases(
    val getConversations: GetConversations,
    val getAuthState: GetAuthState,
    val saveAuthToken: SaveAuthToken,
    val addNotificationToken: AddNotificationToken,
    val getMessages: GetMessages,
    val getPagingMessagesAfter: GetPagingMessagesAfter,
    val getPagingMessagesBefore: GetPagingMessagesBefore,
    val getPagingConversations: GetPagingConversations,
    val sendMessage: SendMessage
)
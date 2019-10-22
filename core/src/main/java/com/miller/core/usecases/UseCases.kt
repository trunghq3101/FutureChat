package com.miller.core.usecases

data class UseCases(
    val loadUserInfo: LoadUserInfo,
    val addNotificationToken: AddNotificationToken,
    val loadPagedMessagesAfter: LoadPagedMessagesAfter,
    val loadPagedMessagesBefore: LoadPagedMessagesBefore,
    val loadPagedConversations: LoadPagedConversations,
    val sendMessage: SendMessage,
    val receiveMessage: ReceiveMessage,
    val saveMessageLocal: SaveMessageLocal
)
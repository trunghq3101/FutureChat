package com.miller.core.domain.model

data class NewConversation(
    val avatarUrl: String,
    val followers: List<String>,
    val title: String,
    val lastMessage: String
)
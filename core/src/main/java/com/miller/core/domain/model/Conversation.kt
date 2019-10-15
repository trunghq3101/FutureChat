package com.miller.core.domain.model

data class Conversation(
    val id: String,
    val avatarUrl: String,
    val followers: List<String>,
    val title: String,
    val lastMessage: String
)
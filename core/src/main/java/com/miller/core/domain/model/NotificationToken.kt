package com.miller.core.domain.model

data class NotificationToken(
    val userId: String,
    val tokens: List<String>
)
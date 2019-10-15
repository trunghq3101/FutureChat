package com.miller.futurechat.framework.model

import com.google.firebase.firestore.DocumentId

/**
 * Created by Miller on 15/10/2019
 */

data class ConversationEntity(
    @DocumentId
    val id: String,
    val avatarUrl: String,
    val followers: List<String>,
    val title: String,
    val lastMessage: String
) {
    constructor() : this("", "", listOf(), "", "")
}
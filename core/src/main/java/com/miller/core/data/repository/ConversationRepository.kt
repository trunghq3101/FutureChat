package com.miller.core.data.repository

import com.miller.core.data.datasource.ConversationDataSource

class ConversationRepository(private val dataSource: ConversationDataSource) {
    fun getPagedFollowing(authToken: String, lastConvId: String?) = dataSource.readPagedFollowing(authToken, lastConvId)
}
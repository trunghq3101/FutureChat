package com.miller.core.data.repository

import com.miller.core.data.datasource.NewConversationDataSource
import com.miller.core.domain.model.NewConversation

class NewConversationRepository(
    private val dataSource: NewConversationDataSource
) {
    fun addNew(cv: NewConversation) = dataSource.create(cv)
}
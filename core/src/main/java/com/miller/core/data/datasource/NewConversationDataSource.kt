package com.miller.core.data.datasource

import com.miller.core.domain.model.NewConversation
import io.reactivex.Single

interface NewConversationDataSource {
    fun create(cv: NewConversation): Single<String>
}
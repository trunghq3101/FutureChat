package com.miller.core.data.datasource

import com.miller.core.domain.model.Message
import io.reactivex.Single

interface LocalMessageDataSource : MessageDataSource {
    fun createMessage(msg: Message): Single<Int>
}
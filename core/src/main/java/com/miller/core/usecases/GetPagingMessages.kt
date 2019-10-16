package com.miller.core.usecases

import com.miller.core.data.repository.MessageRepository
import com.miller.core.domain.model.Message

/**
 * Created by Miller on 16/10/2019
 */

class GetPagingMessages(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(conversationId: String, lastItem: Message?) =
        messageRepository.getPagingMessages(conversationId, lastItem)
}
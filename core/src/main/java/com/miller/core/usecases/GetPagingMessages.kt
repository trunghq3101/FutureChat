package com.miller.core.usecases

import com.miller.core.data.repository.MessageRepository

/**
 * Created by Miller on 16/10/2019
 */

class GetPagingMessages(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(conversationId: String, lastMsgId: String?) =
        messageRepository.getPagingMessages(conversationId, lastMsgId)
}
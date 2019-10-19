package com.miller.core.usecases

import com.miller.core.data.repository.MessageRepository

/**
 * Created by Miller on 17/10/2019
 */

class GetPagingMessagesBefore(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(convId: String, firstMsgId: Int) =
        messageRepository.getPagingMessagesBefore(convId, firstMsgId)
}
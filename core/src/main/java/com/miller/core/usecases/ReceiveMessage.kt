package com.miller.core.usecases

import com.miller.core.data.repository.MessageRepository
import com.miller.core.domain.model.Message

/**
 * Created by Miller on 21/10/2019
 */

class ReceiveMessage(
    private val msgRepo: MessageRepository
) {
    operator fun invoke(msg: Message) = msgRepo.saveMessageLocal(msg)
}
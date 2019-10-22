package com.miller.core.usecases

import com.miller.core.data.repository.MessageRepository
import com.miller.core.domain.model.Message

class SaveMessageLocal(
    private val msgRepo: MessageRepository
) {
    operator fun invoke(msg: Message) = msgRepo.saveMessageLocal(msg)
}
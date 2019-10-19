package com.miller.core.usecases

import com.miller.core.data.repository.MessageRepository
import com.miller.core.domain.model.Message

class SendMessage(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(msg: Message) =
        messageRepository.saveMessageLocal(msg).flatMapCompletable { newId ->
            messageRepository.saveMessageRemote(
                msg.copy(
                    id = newId
                )
            )
        }
}
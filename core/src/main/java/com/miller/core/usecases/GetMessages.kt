package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.MessageRepository
import com.miller.core.utils.UseCaseUtils.withAuthenticated

class GetMessages(
    private val messageRepository: MessageRepository,
    private val authRepository: AuthenticationRepository
) {
    operator fun invoke(conversationId: String) = withAuthenticated(authRepository) {
        messageRepository.getMessages(it, conversationId)
    }
}
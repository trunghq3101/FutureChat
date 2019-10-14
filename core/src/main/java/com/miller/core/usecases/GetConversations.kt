package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.ConversationRepository
import com.miller.core.utils.UseCaseUtils.withAuthenticated

class GetConversations(
    private val conversationRepository: ConversationRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() = withAuthenticated(authenticationRepository) {
        conversationRepository.getConversations(it)
    }
}
package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.ConversationRepository
import com.miller.core.utils.UseCaseUtils

/**
 * Created by Miller on 17/10/2019
 */

class GetPagingConversations(
    private val authenticationRepository: AuthenticationRepository,
    private val conversationRepository: ConversationRepository
) {
    operator fun invoke(lastConvId: String?) = UseCaseUtils.withAuthenticated(authenticationRepository) {
        conversationRepository.getPagingConversations(it, lastConvId)
    }
}
package com.miller.core.usecases

import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.NewConversationRepository
import com.miller.core.data.repository.UserRepository
import com.miller.core.domain.model.NewConversation
import io.reactivex.Completable

class CreateNewConversation(
    private val newConversationRepository: NewConversationRepository,
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(
        avatarUrl: String,
        title: String,
        defaultMsg: String
    ): Completable {
        val userId = authenticationRepository.getToken()
        return newConversationRepository.addNew(
            NewConversation(
                avatarUrl = avatarUrl,
                followers = listOf(userId),
                title = title,
                lastMessage = defaultMsg
            )
        ).flatMapCompletable {
            userRepository.addConversation(userId, it)
        }
    }
}
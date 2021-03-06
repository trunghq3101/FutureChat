package com.miller.futurechat.presentation.conversations.topicfollowing

import androidx.paging.DataSource
import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.ConversationRepository
import com.miller.core.domain.model.Conversation
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.framework.model.mapToFramework
import com.miller.paging.PagingDataLoader
import io.reactivex.Single

class TopicFollowingPagingDataLoader(
    private val authenticationRepository: AuthenticationRepository,
    private val conversationRepository: ConversationRepository,
    private val appDao: AppDao
) : PagingDataLoader<Conversation> {
    override fun fetchPageFromLocal(): DataSource.Factory<Int, Conversation> {
        return appDao.getFollowingConversations().map {
            it.mapToDomain()
        }
    }

    override fun fetchBefore(firstItem: Conversation): Single<List<Conversation>> {
        return Single.create { emitter ->
            emitter.onSuccess(listOf())
        }
    }

    override fun fetchAfter(lastItem: Conversation?): Single<List<Conversation>> {
        return conversationRepository.getPagedFollowing(
            authenticationRepository.getToken(),
            lastItem?.id
        )
    }

    override fun savePageToLocal(items: List<Conversation>): Single<List<Long>> {
        return Single.create { emitter ->
            try {
                val result =
                    appDao.upsertFollowingConversationsSync(items.map { it.mapToFramework() })
                if (result != -1) {
                    emitter.onSuccess(listOf())
                } else {
                    emitter.onError(Throwable())
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun clearPageInLocal(): Single<Void> {
        return appDao.clearConversations()
    }

}
package com.miller.futurechat.presentation.conversations.topicnewest

import androidx.paging.DataSource
import com.miller.core.data.repository.AuthenticationRepository
import com.miller.core.data.repository.ConversationRepository
import com.miller.core.domain.model.Conversation
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.framework.model.mapToFramework
import com.miller.paging.PagingDataLoader
import io.reactivex.Single

class TopicNewestPagingDataLoader(
    private val authenticationRepository: AuthenticationRepository,
    private val conversationRepository: ConversationRepository,
    private val appDao: AppDao
) : PagingDataLoader<Conversation> {
    override fun fetchPageFromLocal(): DataSource.Factory<Int, Conversation> {
        return appDao.getNewestConversations().map { it.mapToDomain() }
    }

    override fun fetchBefore(firstItem: Conversation): Single<List<Conversation>> {
        return Single.just(listOf())
    }

    override fun fetchAfter(lastItem: Conversation?): Single<List<Conversation>> {
        return conversationRepository.getPagedNewest(lastItem?.id)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
package com.miller.futurechat.framework.widget

import androidx.paging.DataSource
import com.miller.core.domain.model.Message
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.framework.model.mapToFramework
import com.miller.paging.LivePagingWrapper
import com.miller.paging.PagingDataLoader
import com.miller.paging.fetchPage
import io.reactivex.Single
import java.lang.Exception

/**
 * Created by Miller on 16/10/2019
 */

class MessagePagingDataLoader(
    private val useCases: UseCases,
    private val appDao: AppDao
) : PagingDataLoader<Message> {

    private var conversationId: String = ""
    private var userId: String = ""

    override fun fetchPageFromLocal(): DataSource.Factory<Int, Message> {
        return appDao.getMessages().map { it.mapToDomain() }
    }

    override fun fetchBefore(firstItem: Message): Single<List<Message>> {
        return useCases.getPagingMessagesBefore(conversationId, firstItem.id)
    }

    override fun fetchAfter(lastItem: Message?): Single<List<Message>> {
        return useCases.getPagingMessagesAfter(conversationId, lastItem?.id)
    }

    override fun savePageToLocal(items: List<Message>): Single<List<Long>> {
        return Single.create { emitter ->
            try {
                val result = appDao.upsertMessagesSync(items.map { it.mapToFramework() })
                emitter.onSuccess(listOf())
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun clearPageInLocal(): Single<Void> {
        return appDao.clearMessages()
    }

    fun fetchPage(userId: String, conversationId: String): LivePagingWrapper<Message> {
        this.userId = userId
        this.conversationId = conversationId
        return fetchPage()
    }

}
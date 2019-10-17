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

    override fun fetchPageFromRemote(lastItem: Message?): Single<List<Message>> {
        return useCases.getPagingMessages(conversationId, lastItem?.id)
    }

    override fun savePageToLocal(items: List<Message>): Single<List<Long>> {
        return appDao.insertMessages(items.map { it.mapToFramework() })
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
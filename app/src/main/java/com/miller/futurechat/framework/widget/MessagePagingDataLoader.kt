package com.miller.futurechat.framework.widget

import androidx.paging.DataSource
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.framework.model.MessageEntity
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
) : PagingDataLoader<MessageEntity> {

    private var conversationId: String = ""

    override fun fetchPageFromLocal(): DataSource.Factory<Int, MessageEntity> {
        return appDao.getMessages()
    }

    override fun fetchPageFromRemote(lastItem: MessageEntity?): Single<List<MessageEntity>> {
        return useCases.getPagingMessages(conversationId, lastItem?.mapToDomain()).map { list ->
            list.map { it.mapToFramework() }
        }
    }

    override fun savePageToLocal(items: List<MessageEntity>): Single<List<Long>> {
        return appDao.insertMessages(items)
    }

    override fun clearPageInLocal(): Single<Void> {
        return appDao.clearMessages()
    }

    fun fetchPage(conversationId: String): LivePagingWrapper<MessageEntity> {
        this.conversationId = conversationId
        return fetchPage()
    }

}
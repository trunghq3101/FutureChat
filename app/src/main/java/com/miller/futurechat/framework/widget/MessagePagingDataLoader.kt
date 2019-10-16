package com.miller.futurechat.framework.widget

import androidx.paging.DataSource
import com.miller.core.domain.model.Message
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.presentation.model.MessageItem
import com.miller.futurechat.presentation.model.mapToPresentation
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
) : PagingDataLoader<MessageItem> {

    private var conversationId: String = ""
    private var userId: String = ""

    override fun fetchPageFromLocal(): DataSource.Factory<Int, MessageItem> {
        return appDao.getMessages()
    }

    override fun fetchPageFromRemote(lastItem: MessageItem?): Single<List<MessageItem>> {
        return useCases.getPagingMessages(conversationId, lastItem?.id).map { list ->
            list.mapIndexed { index: Int, message: Message ->
                message.mapToPresentation(
                    userId,
                    list.getOrNull(index - 1),
                    list.getOrNull(index + 1)
                )
            }
        }
    }

    override fun savePageToLocal(items: List<MessageItem>): Single<List<Long>> {
        return appDao.insertMessages(items)
    }

    override fun clearPageInLocal(): Single<Void> {
        return appDao.clearMessages()
    }

    fun fetchPage(userId: String, conversationId: String): LivePagingWrapper<MessageItem> {
        this.userId = userId
        this.conversationId = conversationId
        return fetchPage()
    }

}
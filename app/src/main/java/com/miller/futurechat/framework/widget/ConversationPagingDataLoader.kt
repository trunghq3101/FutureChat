package com.miller.futurechat.framework.widget

import androidx.paging.DataSource
import com.miller.core.domain.model.Conversation
import com.miller.core.usecases.UseCases
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.framework.model.mapToFramework
import com.miller.paging.PagingDataLoader
import io.reactivex.Single

/**
 * Created by Miller on 17/10/2019
 */

class ConversationPagingDataLoader(
    private val useCases: UseCases,
    private val appDao: AppDao
) : PagingDataLoader<Conversation> {
    override fun fetchPageFromLocal(): DataSource.Factory<Int, Conversation> {
        return appDao.getConversations().map { it.mapToDomain() }
    }

    override fun fetchPageFromRemote(lastItem: Conversation?): Single<List<Conversation>> {
        return useCases.getPagingConversations(lastItem?.id)
    }

    override fun savePageToLocal(items: List<Conversation>): Single<List<Long>> {
        return appDao.insertConversations(items.map { it.mapToFramework() })
    }

    override fun clearPageInLocal(): Single<Void> {
        return appDao.clearConversations()
    }

}
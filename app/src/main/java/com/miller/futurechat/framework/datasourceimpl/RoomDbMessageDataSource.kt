package com.miller.futurechat.framework.datasourceimpl

import com.miller.core.data.datasource.LocalMessageDataSource
import com.miller.core.domain.model.Message
import com.miller.futurechat.framework.db.AppDao
import com.miller.futurechat.framework.model.mapToDomain
import com.miller.futurechat.framework.model.mapToFramework
import io.reactivex.Single

class RoomDbMessageDataSource(
    private val appDao: AppDao
) : LocalMessageDataSource {

    override fun readMessages(authToken: String, conversationId: String): Single<List<Message>> {
        return appDao.getOldestMessages().map { list ->
            list.map { it.mapToDomain() }
        }
    }

    override fun createMessage(msg: Message): Single<Int> {
        return appDao.insertMessage(msg.mapToFramework()).map { it.toInt() }
    }
}
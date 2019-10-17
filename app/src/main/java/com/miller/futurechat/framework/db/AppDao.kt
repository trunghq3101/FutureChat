package com.miller.futurechat.framework.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miller.futurechat.framework.model.ConversationEntity
import com.miller.futurechat.framework.model.MessageEntity
import com.miller.futurechat.presentation.model.MessageItem
import io.reactivex.Single

/**
 * Created by Miller on 16/10/2019
 */

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessages(messages: List<MessageEntity>): Single<List<Long>>

    @Query("DELETE FROM messages")
    fun clearMessages(): Single<Void>

    @Query("SELECT * FROM messages")
    fun getMessages(): DataSource.Factory<Int, MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversations(conversations: List<ConversationEntity>): Single<List<Long>>

    @Query("DELETE FROM conversations")
    fun clearConversations(): Single<Void>

    @Query("SELECT * FROM conversations")
    fun getConversations(): DataSource.Factory<Int, ConversationEntity>
}
package com.miller.futurechat.framework.db

import androidx.paging.DataSource
import androidx.room.*
import com.miller.futurechat.framework.model.ConversationEntity
import com.miller.futurechat.framework.model.MessageEntity
import io.reactivex.Single

/**
 * Created by Miller on 16/10/2019
 */

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessages(messages: List<MessageEntity>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessagesSync(messages: List<MessageEntity>): List<Long>

    @Update
    fun updateMessagesSync(messages: List<MessageEntity>): Int

    @Transaction
    fun upsertMessagesSync(messages: List<MessageEntity>): Int {
        return insertMessagesSync(messages).mapIndexed { index: Int, result: Long ->
            if (result == -1L) messages[index] else null
        }.filterNotNull().let {
            updateMessagesSync(it)
        }
    }

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
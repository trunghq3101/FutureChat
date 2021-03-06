package com.miller.futurechat.framework.db

import androidx.paging.DataSource
import androidx.room.*
import com.miller.futurechat.framework.constants.FOLLOWING_TOPIC_TYPE
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

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getMessages(): DataSource.Factory<Int, MessageEntity>

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getOldestMessages(): Single<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(messageEntity: MessageEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversations(conversations: List<ConversationEntity>): Single<List<Long>>

    /*******
    * Conversations
     *******/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConversationsSync(conversations: List<ConversationEntity>): List<Long>

    @Update
    fun updateConversationsSync(conversations: List<ConversationEntity>): Int

    @Transaction
    fun upsertFollowingConversationsSync(conversations: List<ConversationEntity>): Int {
        return insertConversationsSync(conversations)
            .mapIndexed { index: Int, result: Long ->
                if (result == -1L) conversations[index] else null
            }
            .filterNotNull()
            .map {
                it.apply {
                    it.topicType = it.topicType or FOLLOWING_TOPIC_TYPE
                }
            }.let {
                updateConversationsSync(it)
            }
    }

    @Transaction
    fun upsertNewestConversationsSync(conversations: List<ConversationEntity>): Int {
        return insertConversationsSync(conversations)
            .mapIndexed { index: Int, result: Long ->
                if (result == -1L)
            }
    }

    @Query("DELETE FROM conversations")
    fun clearConversations(): Single<Void>

    @Query("SELECT * FROM conversations")
    fun getConversations(): DataSource.Factory<Int, ConversationEntity>

    @Query("SELECT * FROM conversations WHERE topicType & (2 << 2) = (2 << 2)")
    fun getFollowingConversations(): DataSource.Factory<Int, ConversationEntity>

    @Query("SELECT * FROM conversations")
    fun getNewestConversations(): DataSource.Factory<Int, ConversationEntity>
}
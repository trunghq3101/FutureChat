package com.miller.repository

import com.miller.conversations.model.ConversationItem
import com.miller.conversations.repository.ConversationsRepository
import com.miller.datasource.firebase.FirestoreDataSource
import com.miller.datasource.sharePref.SharedPrefDataSource
import io.reactivex.Single

/**
 * Created by Miller on 20/09/2019
 */

class ConversationsRepositoryImpl(
    private val firestoreDataSource: FirestoreDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource
) : ConversationsRepository {

    override fun fetchConversations(): Single<List<ConversationItem>> {
        return firestoreDataSource.fetchConversations(sharedPrefDataSource.readUserId())
    }

}
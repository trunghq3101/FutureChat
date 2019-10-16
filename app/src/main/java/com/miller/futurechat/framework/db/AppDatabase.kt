package com.miller.futurechat.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miller.futurechat.framework.model.MessageEntity

/**
 * Created by Miller on 16/10/2019
 */

@Database(entities = [MessageEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun create(ctx: Context): AppDatabase {
            return INSTANCE ?: run {
                synchronized(AppDatabase::class.java) {
                    Room.databaseBuilder(
                        ctx.applicationContext,
                        AppDatabase::class.java,
                        "FutureChat"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
    }

    abstract fun Dao(): AppDao
}
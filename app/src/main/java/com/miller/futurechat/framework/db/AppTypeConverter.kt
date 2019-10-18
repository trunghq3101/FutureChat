package com.miller.futurechat.framework.db

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import com.google.gson.Gson
import com.miller.futurechat.utils.fromJson

class AppTypeConverter {

    @TypeConverter
    fun fromListString(type: List<String>): String = Gson().toJson(type)

    @TypeConverter
    fun toListString(value: String): List<String> = Gson().fromJson(value)

    @TypeConverter
    fun fromTimestamp(type: Timestamp): String = Gson().toJson(type)

    @TypeConverter
    fun toTimestamp(value: String): Timestamp = Gson().fromJson(value)
}
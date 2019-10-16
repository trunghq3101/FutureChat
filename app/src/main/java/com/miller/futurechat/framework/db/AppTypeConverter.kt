package com.miller.futurechat.framework.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.miller.futurechat.presentation.model.type.OwnerType
import com.miller.futurechat.presentation.model.type.RelativePositionType
import com.miller.futurechat.utils.fromJson

class AppTypeConverter {

    @TypeConverter
    fun fromOwnerType(type: OwnerType): String = Gson().toJson(type)

    @TypeConverter
    fun toOwnerType(value: String): OwnerType = Gson().fromJson(value)

    @TypeConverter
    fun fromRelativePositionType(type: RelativePositionType): String = Gson().toJson(type)

    @TypeConverter
    fun toRelativePositionType(value: String): RelativePositionType = Gson().fromJson(value)
}
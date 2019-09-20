package com.miller.datasource.sharePref

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Miller on 19/09/2019
 */

class SharedPrefApi(private val context: Context) {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun set(key: String, value: Int) = sharedPreferences.edit().apply { putInt(key, value) }.apply()

    fun set(key: String, value: String) = sharedPreferences.edit().apply { putString(key, value) }.apply()

    fun get(key: String, defValue: Int) = sharedPreferences.getInt(key, defValue)

    fun get(key: String, defValue: String) = sharedPreferences.getString(key, defValue)

    fun clear() = sharedPreferences.edit().apply { clear() }.apply()

    fun remove(key: String) = sharedPreferences.edit().apply { remove(key) }.apply()

    fun contains(key: String) = sharedPreferences.contains(key)
}
package com.miller.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Miller on 19/09/2019
 */

data class TokenEntity(
    @SerializedName(KEY_USER_ID)
    val userId: String,
    @SerializedName(KEY_TOKENS)
    val tokens: List<String>
) {
    companion object {
        const val KEY_USER_ID = "userId"
        const val KEY_TOKENS = "tokens"
    }
}
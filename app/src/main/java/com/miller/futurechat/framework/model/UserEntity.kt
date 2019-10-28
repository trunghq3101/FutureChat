package com.miller.futurechat.framework.model

/**
 * Created by Miller on 21/10/2019
 */

data class UserEntity(
    val id: String,
    var avatarUrl: String? = null
) {
    constructor() : this(
        id = "",
        avatarUrl = ""
    )
}
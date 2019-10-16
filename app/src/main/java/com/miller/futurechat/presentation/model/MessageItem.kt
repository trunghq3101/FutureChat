package com.miller.futurechat.presentation.model

import com.miller.futurechat.presentation.model.type.OwnerType
import com.miller.futurechat.presentation.model.type.RelativePositionType

data class MessageItem(
    val id: String,
    val contentText: String,
    val ownerType: OwnerType,
    val relativePosition: RelativePositionType
)
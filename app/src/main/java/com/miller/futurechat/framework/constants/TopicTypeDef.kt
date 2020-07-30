package com.miller.futurechat.framework.constants

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    flag = true,
    value = [NEWEST_TOPIC_TYPE, FOLLOWING_TOPIC_TYPE]
)
annotation class TopicTypeDef

const val NEWEST_TOPIC_TYPE = 2 shl 1
const val FOLLOWING_TOPIC_TYPE = 2 shl 2
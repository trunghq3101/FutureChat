package com.miller.futurechat.utils

import com.google.firebase.Timestamp
import java.util.*

fun Long.toTimestamp(): Timestamp {
    return Timestamp(Date(this))
}
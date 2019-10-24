package com.miller.futurechat.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Miller on 24/10/2019
 */

fun RecyclerView.findFirstVisiblePosition(): Int {
    return (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
}
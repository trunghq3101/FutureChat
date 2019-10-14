package com.miller.futurechat.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("booleanVisibility")
fun View.setBooleanVisibility(isVisible: Boolean?) {
    isVisible ?: return
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}
package com.miller.futurechat.utils

import android.util.Log
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["isLoading"])
fun ContentLoadingProgressBar.setIsLoading(isLoading: Boolean?) {
    Log.d("------>"," : progress $isLoading")
    isLoading?.let {
        if (it) show() else hide()
    }
}
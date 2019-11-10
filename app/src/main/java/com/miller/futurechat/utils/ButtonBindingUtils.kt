package com.miller.futurechat.utils

import android.util.Log
import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["isLoading"])
fun Button.setIsLoading(isLoading: Boolean?) {
    Log.d("------>"," : btn $isLoading")
    isLoading?.let {
        isEnabled = !it
    } ?: run {
        isEnabled = true
    }
}
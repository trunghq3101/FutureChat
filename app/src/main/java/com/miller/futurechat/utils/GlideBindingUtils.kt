package com.miller.futurechat.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Miller on 20/09/2019
 */

@BindingAdapter("glideCircularUrl")
fun ImageView.setGlideCircularUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .apply(
                RequestOptions.circleCropTransform()
            )
            .into(this)
    }
}

@BindingAdapter("glideUrl")
fun ImageView.setGlideUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}
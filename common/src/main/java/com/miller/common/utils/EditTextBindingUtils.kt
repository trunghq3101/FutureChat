package com.miller.common.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter

object EditTextBindingUtils {
    interface OnTextExistListener {
        fun onTextEmpty()
        fun onTextExist()
    }
}

@BindingAdapter(value = ["onTextExist"])
fun EditText.setOnTextExistListener(listener: EditTextBindingUtils.OnTextExistListener?) {
    listener ?: return
    addTextChangedListener {
        if (it?.isNotEmpty() == true) {
            listener.onTextExist()
        } else {
            listener.onTextEmpty()
        }
    }
}
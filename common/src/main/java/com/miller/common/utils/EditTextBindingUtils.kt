package com.miller.common.utils

import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.miller.common.utils.EditTextBindingUtils.DRAWABLE_RIGHT

object EditTextBindingUtils {
    const val DRAWABLE_LEFT = 0
    const val DRAWABLE_TOP = 1
    const val DRAWABLE_RIGHT = 2
    const val DRAWABLE_BOTTOM = 3
}

@BindingAdapter(value = ["drawableClickListener"])
fun EditText.setDrawableClickListener(listener:() -> Unit) {
    val gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return compoundDrawables.getOrNull(DRAWABLE_RIGHT)?.bounds?.let {
                if (e.rawX >= right - it.width()) {
                    listener.invoke()
                    true
                } else {
                    false
                }
            } ?: run {
                false
            }
        }
    })
    setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
    }
}
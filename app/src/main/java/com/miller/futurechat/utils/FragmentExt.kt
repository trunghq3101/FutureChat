package com.miller.futurechat.utils

import androidx.fragment.app.Fragment

fun Fragment.openFragment(
    fragment: Fragment,
    containerId: Int,
    addToBackStack: Boolean? = false,
    tag: String? = null
) {
    activity?.supportFragmentManager?.beginTransaction()?.apply {
        add(containerId, fragment, tag)
        if (addToBackStack == true) addToBackStack(tag)
        commit()
    }
}
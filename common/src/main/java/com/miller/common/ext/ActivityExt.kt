package com.miller.common.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.openFragment(
    fragment: Fragment,
    containerId: Int,
    addToBackStack: Boolean? = false,
    tag: String? = null
) {
    supportFragmentManager.beginTransaction().apply {
        add(containerId, fragment, tag)
        if (addToBackStack == true) addToBackStack(tag)
        commit()
    }
}
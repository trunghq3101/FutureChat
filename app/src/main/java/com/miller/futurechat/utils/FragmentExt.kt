package com.miller.futurechat.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

fun Fragment.setupToolbar(toolbar: Toolbar, hasTitle: Boolean? = true, hasMenu: Boolean? = false) {
    (activity as? AppCompatActivity)?.apply {
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, findNavController())
        supportActionBar?.run {
            setHasOptionsMenu(hasMenu == true)
            setDisplayShowTitleEnabled(hasTitle == true)
        }
    }
}
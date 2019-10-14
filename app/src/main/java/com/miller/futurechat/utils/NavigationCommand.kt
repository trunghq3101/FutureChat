package com.miller.futurechat.utils

import androidx.navigation.NavDirections

/**
 * Created by Miller on 23/09/2019
 */

sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
}
package com.miller.common.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.miller.common.navigation.NavigationCommand
import com.miller.common.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Miller on 20/09/2019
 */

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val navCommands = SingleLiveEvent<NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navCommands.postValue(NavigationCommand.To(directions))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
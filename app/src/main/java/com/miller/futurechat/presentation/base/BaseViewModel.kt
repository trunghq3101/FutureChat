package com.miller.futurechat.presentation.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.miller.futurechat.utils.NavigationCommand
import com.miller.futurechat.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Miller on 20/09/2019
 */

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val navCommands =
        com.miller.futurechat.utils.SingleLiveEvent<com.miller.futurechat.utils.NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navCommands.postValue(com.miller.futurechat.utils.NavigationCommand.To(directions))
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun onLoadFail(t: Throwable?) {
        t?.printStackTrace()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
package com.miller.futurechat.presentation.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.miller.core.usecases.UseCases
import com.miller.futurechat.utils.NavigationCommand
import com.miller.futurechat.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Miller on 20/09/2019
 */

abstract class BaseViewModel : ViewModel(), KoinComponent {

    abstract val useCases: UseCases
    private val compositeDisposable: CompositeDisposable by inject()

    val navCommands =
        SingleLiveEvent<NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navCommands.postValue(NavigationCommand.To(directions))
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
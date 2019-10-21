package com.miller.futurechat.presentation

import android.util.Log
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.futurechat.utils.SingleLiveEvent
import org.koin.core.inject

/**
 * Created by Miller on 21/10/2019
 */

class MainViewModel : BaseViewModel() {

    override val useCases: UseCases by inject()

    var authToken: String? = null

    /*
    Observables
     */
    val loggedInStatus = SingleLiveEvent<AuthState>()

    fun loadLoggedInStatus() {
        useCases.getAuthState()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    loggedInStatus.value = it
                    authToken = (it as? AuthState.LoggedIn)?.token
                },
                {
                    loggedInStatus.value = AuthState.LoggedOut
                }
            ).apply {
                addDisposable(this)
            }
    }

    fun saveAuthToken(token: String) {
        useCases.saveAuthToken(token)
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    loadLoggedInStatus()
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }

    fun saveFCMToken(token: String) {
        authToken?.let {
            useCases.addNotificationToken(token, it)
                .compose(SchedulersUtils.applyAsyncSchedulersSingle())
                .subscribe(
                    {
                        Log.d("----->","MainViewModel - saveFCMToken : ")
                    },
                    { t ->
                        onLoadFail(t)
                    }
                ).apply {
                    addDisposable(this)
                }
        }
    }
}
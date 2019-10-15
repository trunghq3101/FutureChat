package com.miller.futurechat.presentation.login

import android.util.Log
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.utils.SchedulersUtils.applyAsyncSchedulersSingle
import com.miller.futurechat.utils.SingleLiveEvent
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState

/**
 * Created by Miller on 25/09/2019
 */

class LoginViewModel(
    private val useCases: UseCases
) : BaseViewModel() {

    var authToken: String? = null

    /*
    Observables
     */
    val loggedInStatus = SingleLiveEvent<AuthState>()
    val fcmTokenSaved = SingleLiveEvent<Boolean>()

    fun loadLoggedInStatus() {
        useCases.getAuthState()
            .compose(applyAsyncSchedulersSingle())
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
            .compose(applyAsyncSchedulersSingle())
            .subscribe(
                {
                    authToken = it
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
                .compose(applyAsyncSchedulersSingle())
                .subscribe(
                    {
                        fcmTokenSaved.value = true
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
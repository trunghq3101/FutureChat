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

                },
                {

                }
            ).apply {
                addDisposable(this)
            }
    }

    fun saveFCMToken(token: String) {
        (loggedInStatus.value as? AuthState.LoggedIn)?.token?.let {
            useCases.addNotificationToken(token, it)
                .compose(applyAsyncSchedulersSingle())
                .subscribe(
                    {
                        fcmTokenSaved.value = true
                    },
                    {
                        it.printStackTrace()
                        Log.d("------>"," : saveFCM error")
                    }
                ).apply {
                    addDisposable(this)
                }
        }
    }
}
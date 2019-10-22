package com.miller.futurechat.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.presentation.model.UserItem
import com.miller.futurechat.presentation.model.mapToPresentation
import com.miller.futurechat.utils.Event
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.futurechat.utils.SingleLiveEvent
import org.koin.core.inject

/**
 * Created by Miller on 21/10/2019
 */

class MainViewModel : BaseViewModel() {

    override val useCases: UseCases by inject()

    private val firebaseAuth: FirebaseAuth by inject()

    private val authStateListener: FirebaseAuth.AuthStateListener by lazy {
        FirebaseAuth.AuthStateListener {
            firebaseAuth.currentUser?.let {
                if (!isLoggedIn(it.uid))
                    _authState.value = Event(AuthState.LoggedIn(it.uid))
            } ?: run {
                if (!isLoggedOut())
                    _authState.value = Event(AuthState.LoggedOut)
            }
        }
    }

    /*
    Observables
     */
    private val _userInfo = MutableLiveData<UserItem>()
    val userInfo: LiveData<UserItem> = _userInfo

    private val _authState = MutableLiveData<Event<AuthState>>()
    val authState: LiveData<Event<AuthState>> = _authState

    val signingOut = SingleLiveEvent<Boolean>()
    /*
    -----------
     */

    fun loadAuthState() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun loadUserInfo() {
        useCases.loadUserInfo()
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    _userInfo.value = it.mapToPresentation()
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }

    fun registerFCMInstanceId() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d("----->", "MainActivity - registerFCMInstanceId : Unsuccessfully")
                    return@addOnCompleteListener
                }
                it.result?.token?.let { token ->
                    saveFCMToken(token)
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun signOut() {
        signingOut.value = true
    }

    fun peekUserId() = (authState.value?.peekContent() as? AuthState.LoggedIn)?.token

    private fun saveFCMToken(token: String) {
        useCases.addNotificationToken(token)
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    Log.d("----->", "MainViewModel - saveFCMToken : ")
                },
                { t ->
                    onLoadFail(t)
                }
            ).apply {
                addDisposable(this)
            }
    }

    private fun isLoggedIn(userId: String) =
        (authState.value?.peekContent() as? AuthState.LoggedIn)?.token == userId

    private fun isLoggedOut() =
        (authState.value?.peekContent() is AuthState.LoggedOut)
}
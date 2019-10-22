package com.miller.futurechat.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.miller.core.usecases.UseCases
import com.miller.core.usecases.model.AuthState
import com.miller.futurechat.presentation.base.BaseViewModel
import com.miller.futurechat.presentation.model.UserItem
import com.miller.futurechat.utils.Event
import com.miller.futurechat.utils.SchedulersUtils
import com.miller.futurechat.utils.SingleLiveEvent
import org.koin.core.inject

/**
 * Created by Miller on 21/10/2019
 */

class MainViewModel : BaseViewModel() {

    override val useCases: UseCases by inject()

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val authStateListener: FirebaseAuth.AuthStateListener by lazy {
        FirebaseAuth.AuthStateListener {
            firebaseAuth.currentUser?.let {
                if (firebaseUser.value != it) {
                    _firebaseUser.value = it
                    _authState.value = Event(AuthState.LoggedIn(it.uid))
                }
            } ?: run {
                if (authState.value?.peekContent() !is AuthState.LoggedOut) _authState.value =
                    Event(AuthState.LoggedOut)
            }
        }
    }

    /*
    Observables
     */
    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> = _firebaseUser

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

    fun saveAuthToken(token: String) {
        useCases.saveAuthToken(token)
            .compose(SchedulersUtils.applyAsyncSchedulersSingle())
            .subscribe(
                {
                    loadAuthState()
                },
                {
                    onLoadFail(it)
                }
            ).apply {
                addDisposable(this)
            }
    }

    fun saveFCMToken(token: String) {
        firebaseUser.value?.uid?.let {
            useCases.addNotificationToken(token, it)
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
    }

    fun signOut() {
        signingOut.value = true
    }
}
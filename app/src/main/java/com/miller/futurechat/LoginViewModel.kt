package com.miller.futurechat

import com.miller.common.base.BaseViewModel
import com.miller.common.utils.SchedulersUtils
import com.miller.common.utils.SingleLiveEvent
import com.miller.repository.FirebaseRepository
import com.miller.repository.UserRepository

/**
 * Created by Miller on 25/09/2019
 */

class LoginViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val fcmTokenSaved = SingleLiveEvent<Boolean>()

    fun readUserIdFromSharedPref() = userRepository.readUserId()

    fun saveUserIdToSharedPref(uid: String) {
        userRepository.saveUserId(uid)
    }

    fun saveFCMToken(token: String) {
        val uid = userRepository.readUserId()
        if (uid.isNotEmpty()) {
            firebaseRepository.saveRegistrationToken(token, uid)
                .compose(SchedulersUtils.applyAsyncSchedulersSingle())
                .subscribe(
                    {
                        fcmTokenSaved.value = true
                    },
                    {
                        // TODO: Show error dialog
                    }
                )
                .apply {
                    addDisposable(this)
                }
        }
    }
}
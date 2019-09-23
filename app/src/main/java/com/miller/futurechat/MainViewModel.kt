package com.miller.futurechat

import com.miller.common.base.BaseViewModel
import com.miller.repository.FirebaseRepository
import com.miller.repository.UserRepository

/**
 * Created by Miller on 19/09/2019
 */

class MainViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun readUserIdFromSharedPref() = userRepository.readUserId()

    fun saveUserIdToSharedPref(uid: String) {
        userRepository.saveUserId(uid)
    }

    fun saveFCMToken(token: String) {
        val uid = userRepository.readUserId()
        if (uid.isNotEmpty()) {
            firebaseRepository.saveRegistrationToken(token, uid)
        }
    }
}
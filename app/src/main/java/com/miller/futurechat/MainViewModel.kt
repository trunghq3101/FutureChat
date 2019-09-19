package com.miller.futurechat

import androidx.lifecycle.ViewModel
import com.miller.repository.FirebaseRepository
import com.miller.repository.UserRepository

/**
 * Created by Miller on 19/09/2019
 */

class MainViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository
) : ViewModel() {

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
package com.miller.futurechat

import androidx.lifecycle.ViewModel
import com.miller.repository.UserRepository

/**
 * Created by Miller on 19/09/2019
 */

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun saveUserIdToSharedPref(uid: String) {
        userRepository.saveUserId(uid)
    }
}
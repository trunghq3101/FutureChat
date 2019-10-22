package com.miller.futurechat.presentation.profile

import com.miller.core.usecases.UseCases
import com.miller.futurechat.presentation.base.BaseViewModel
import org.koin.core.inject

/**
 * Created by Miller on 22/10/2019
 */

class ProfileViewModel : BaseViewModel() {
    override val useCases: UseCases by inject()

}
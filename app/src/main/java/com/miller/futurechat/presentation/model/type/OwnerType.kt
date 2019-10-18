package com.miller.futurechat.presentation.model.type

/**
 * Created by Miller on 16/10/2019
 */

sealed class OwnerType {
    object Me: OwnerType()
    object Other: OwnerType()
}
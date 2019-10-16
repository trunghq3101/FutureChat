package com.miller.futurechat.presentation.model.type

/**
 * Created by Miller on 16/10/2019
 */

sealed class RelativePositionType {
    object Top: RelativePositionType()
    object Mid: RelativePositionType()
    object Bot: RelativePositionType()
    object Single: RelativePositionType()
}
package com.hit.hitgisproject.managers.api

import kotlinx.coroutines.flow.Flow

interface IEventManager {
    fun emitLocationSearcherClicked(isClicked: Boolean)
    fun getLocationSearcherClicked(): Flow<Boolean>
}
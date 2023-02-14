package com.hit.hitgisproject.managers.impl

import com.hit.hitgisproject.managers.api.IEventManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EventManager: IEventManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val locationSearcherSharedFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override fun emitLocationSearcherClicked(isClicked: Boolean) {
        scope.launch {
            locationSearcherSharedFlow.emit(isClicked)
        }
    }

    override fun getLocationSearcherClicked(): Flow<Boolean> = locationSearcherSharedFlow.asSharedFlow()
}
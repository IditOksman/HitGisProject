package com.hit.hitgisproject.di

import com.hit.hitgisproject.managers.api.IEventManager
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun getEventManager(): IEventManager
}
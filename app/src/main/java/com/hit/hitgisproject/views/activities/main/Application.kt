package com.hit.hitgisproject.views.activities.main

import android.app.Application
import com.hit.hitgisproject.di.ApplicationComponent
import com.hit.hitgisproject.di.ApplicationInjector

class MyApplication: Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = ApplicationInjector.initComponent(this)

    }


}
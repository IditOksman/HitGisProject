package com.hit.hitgisproject.di

import com.hit.hitgisproject.views.activities.main.MyApplication

class ApplicationInjector {

   companion object {
       lateinit var applicationComponent: ApplicationComponent

       fun initComponent(app: MyApplication): ApplicationComponent {

           applicationComponent = DaggerApplicationComponent.builder()
               .applicationModule(ApplicationModule(app))
               .build()
           return applicationComponent
       }
   }

}
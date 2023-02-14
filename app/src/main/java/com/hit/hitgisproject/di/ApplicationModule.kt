package com.hit.hitgisproject.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.hit.hitgisproject.views.activities.main.MyApplication
import com.hit.hitgisproject.managers.api.IEventManager
import com.hit.hitgisproject.managers.impl.EventManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val app: MyApplication
) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideEventManager(): IEventManager {
        return EventManager()
    }
}
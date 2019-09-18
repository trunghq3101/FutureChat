package com.miller.futurechat

import android.app.Application
import com.miller.futurechat.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Miller on 18/09/2019
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(repositoryModule))
        }
    }
}
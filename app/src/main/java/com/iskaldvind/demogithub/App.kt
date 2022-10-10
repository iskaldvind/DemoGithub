package com.iskaldvind.demogithub

import android.app.Application
import com.iskaldvind.demogithub.di.application
import com.iskaldvind.demogithub.di.user
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, user))
        }
    }
}
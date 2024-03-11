package com.compose.kmplibs

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CustomApplication : Application() {
    override fun onCreate() {
        startKoin {
            androidContext(this@CustomApplication)
        }

        super.onCreate()
    }
}
package com.compose.kmplibs

import android.app.Application
import com.compose.kmplibs.data.di.DataModule
import com.compose.kmplibs.data.di.DispatchersModule
import com.compose.kmplibs.di.AppModule
import com.compose.kmplibs.usecases.di.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinApplication
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(
    modules = [
        DispatchersModule::class,
        DataModule::class,
        UseCasesModule::class,
        AppModule::class
    ]
)
class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin<CustomApplication> {
            androidLogger()
            androidContext(this@CustomApplication)
        }
    }
}

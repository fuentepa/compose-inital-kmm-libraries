package com.compose.kmplibs

import android.app.Application
import com.compose.kmplibs.data.di.DataModule
import com.compose.kmplibs.data.di.DispatchersModule
import com.compose.kmplibs.di.AppModule
import com.compose.kmplibs.usecases.di.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(
                DataModule().module,
                UseCasesModule().module,
                AppModule().module,
                DispatchersModule().module
            )
        }
    }
}
package com.compose.kmplibs

import android.app.Application
import com.compose.kmplibs.data.di.DataModule
import com.compose.kmplibs.di.appModule
import com.compose.kmplibs.usecases.di.useCasesModule
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
                //defaultModule
                listOf(
                    DataModule().module,
                    useCasesModule,
                    appModule
                )
            )


        }


    }
}
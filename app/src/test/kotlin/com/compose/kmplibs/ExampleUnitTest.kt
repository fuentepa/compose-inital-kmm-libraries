package com.compose.kmplibs

import com.compose.kmplibs.data.di.DataModule
import com.compose.kmplibs.di.appModule
import com.compose.kmplibs.usecases.di.useCasesModule
import org.junit.Test

import org.junit.Assert.*
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules() {
        appModule.verify()
        useCasesModule.verify()
        DataModule().module.verify()
    }
}
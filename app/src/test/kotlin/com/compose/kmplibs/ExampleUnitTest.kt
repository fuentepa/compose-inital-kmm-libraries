package com.compose.kmplibs

import com.compose.kmplibs.data.di.DataModule
import com.compose.kmplibs.di.AppModule
import com.compose.kmplibs.usecases.di.UseCasesModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.verify.definition
import org.koin.test.verify.injectedParameters
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules() {
        AppModule().module.verify(injections = injectedParameters(
            definition<io.ktor.client.HttpClient>(io.ktor.client.engine.HttpClientEngine::class),
            definition<io.ktor.client.HttpClient>(io.ktor.client.HttpClientConfig::class)
        ))
        UseCasesModule().module.verify()
        DataModule().module.verify()
    }
}
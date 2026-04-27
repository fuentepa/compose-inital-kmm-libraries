package com.compose.kmplibs.usecases.di

import com.compose.kmplibs.data.di.DispatchersModule
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DispatchersModule::class])
@Configuration
@ComponentScan("com.compose.kmplibs.usecases")
class UseCasesModule

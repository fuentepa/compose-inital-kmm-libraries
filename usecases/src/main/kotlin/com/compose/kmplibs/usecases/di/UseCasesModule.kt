package com.compose.kmplibs.usecases.di

import com.compose.kmplibs.usecases.GetAllFilmsUseCase
import com.compose.kmplibs.usecases.GetAllFilmsUseCaseImpl
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

val useCasesModule = module {
    factory<GetAllFilmsUseCase> { GetAllFilmsUseCaseImpl(get()) }
}


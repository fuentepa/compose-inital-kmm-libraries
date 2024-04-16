package com.compose.kmplibs.usecases.di

import com.compose.kmplibs.usecases.GetMovieDetailUseCase
import com.compose.kmplibs.usecases.GetMovieDetailUseCaseImpl
import com.compose.kmplibs.usecases.GetTopRatedMoviesUseCase
import com.compose.kmplibs.usecases.GetTopRatedMoviesUseCaseImpl
import org.koin.dsl.module

val useCasesModule = module {
    factory<GetTopRatedMoviesUseCase> { GetTopRatedMoviesUseCaseImpl(get()) }
    factory<GetMovieDetailUseCase> { GetMovieDetailUseCaseImpl(get()) }
}


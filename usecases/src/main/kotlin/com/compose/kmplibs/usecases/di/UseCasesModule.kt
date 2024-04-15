package com.compose.kmplibs.usecases.di

import com.compose.kmplibs.usecases.GetAllFilmsUseCase
import com.compose.kmplibs.usecases.GetAllFilmsUseCaseImpl
import com.compose.kmplibs.usecases.GetMovieDetailsUseCase
import com.compose.kmplibs.usecases.GetMovieDetailsUseCaseImpl
import com.compose.kmplibs.usecases.GetTopRatedMoviesUseCase
import com.compose.kmplibs.usecases.GetTopRatedMoviesUseCaseImpl
import org.koin.dsl.module

val useCasesModule = module {
    factory<GetAllFilmsUseCase> { GetAllFilmsUseCaseImpl(get()) }

    factory<GetTopRatedMoviesUseCase> { GetTopRatedMoviesUseCaseImpl(get()) }
    factory<GetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
}


package com.compose.kmplibs.data.source

import com.compose.kmplibs.data.remote.response.MovieDetailResponse
import com.compose.kmplibs.data.remote.response.MovieResponse

interface RemoteDataSource {

    suspend fun getTopRatedMovies(): Result<List<MovieResponse>>

    suspend fun getMovieDetails(id: Int): Result<MovieDetailResponse>
}
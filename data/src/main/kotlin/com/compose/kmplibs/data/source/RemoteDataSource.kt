package com.compose.kmplibs.data.source

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result

interface RemoteDataSource {

    suspend fun getTopRatedMovies(): Result<List<Movie>>

    suspend fun getMovieDetails(id: Int): Result<MovieDetail>
}
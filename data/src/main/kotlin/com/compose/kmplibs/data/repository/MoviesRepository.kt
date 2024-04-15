package com.compose.kmplibs.data.repository

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.remote.Result

interface MoviesRepository {

    suspend fun getTopRatedMovies(): Result<List<Movie>>

    suspend fun getMovieDetail(id: Int): Result<Movie>
}
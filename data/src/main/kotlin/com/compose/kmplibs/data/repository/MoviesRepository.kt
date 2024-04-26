package com.compose.kmplibs.data.repository

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result

interface MoviesRepository {

    suspend fun getTopRatedMovies(): Result<List<Movie>>

    suspend fun getMovieDetails(id: Int): Result<MovieDetail>
}
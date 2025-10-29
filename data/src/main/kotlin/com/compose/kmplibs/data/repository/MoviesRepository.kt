package com.compose.kmplibs.data.repository

import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.model.MovieDetail

interface MoviesRepository {
    suspend fun getTopRatedMovies(): Result<List<Movie>>
    suspend fun getMovieDetail(id: Int): Result<MovieDetail>
}
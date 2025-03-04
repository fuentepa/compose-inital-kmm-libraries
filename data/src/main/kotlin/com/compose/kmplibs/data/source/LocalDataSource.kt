package com.compose.kmplibs.data.source

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result

interface LocalDataSource {

    suspend fun isEmpty(): Boolean

    suspend fun getTopRatedMovies(): Result<List<Movie>>

    suspend fun getMovieDetails(id: Int): Result<MovieDetail>

    suspend fun saveTopRatedMovies(movies: List<Movie>)

    suspend fun saveMovieDetail(movieDetail: MovieDetail)
}
package com.compose.kmplibs.data.source


import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.model.MovieDetail

interface LocalDataSource {

    suspend fun isEmpty(): Boolean

    suspend fun getTopRatedMovies(): Result<List<Movie>>

    suspend fun getMovieDetails(id: Int): Result<MovieDetail>

    suspend fun saveTopRatedMovies(movies: List<Movie>)

    suspend fun saveMovieDetail(movieDetail: MovieDetail)
}
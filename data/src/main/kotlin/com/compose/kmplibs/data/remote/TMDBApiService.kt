package com.compose.kmplibs.data.remote

import com.compose.kmplibs.data.model.MovieDetailResponse
import com.compose.kmplibs.data.model.MovieResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface TMDBApiService {
    @GET("movie/top_rated?page=1")
    suspend fun getTopRatedMovies(): List<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path id: Int): MovieDetailResponse
}
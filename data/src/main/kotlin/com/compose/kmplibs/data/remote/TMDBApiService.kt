package com.compose.kmplibs.data.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface TMDBApiService {
    companion object {
        // TODO: Access BuildConfig
        const val API_URL = "https://api.themoviedb.org/3/"
    }

    // TODO: Pager
    @GET("movie/top_rated?page=1")
    suspend fun getTopRatedMovies(): List<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path id: Int): MovieResponse
}
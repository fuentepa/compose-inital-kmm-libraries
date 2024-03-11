package com.compose.data.remote

import de.jensklingenberg.ktorfit.http.GET


interface StarWarsApiService {
    @GET("/api/films")
    suspend fun getAllfilms(): List<FilmEntity>

    companion object {
        const val API_URL = "https://swapi.info"
    }
}

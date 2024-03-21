package com.compose.kmplibs.data.remote

import de.jensklingenberg.ktorfit.http.GET


interface StarWarsApiService {

    companion object {
        const val API_URL = "https://swapi.info/"
    }

    @GET("api/films")
    suspend fun getAllfilms(): List<FilmEntity>
}

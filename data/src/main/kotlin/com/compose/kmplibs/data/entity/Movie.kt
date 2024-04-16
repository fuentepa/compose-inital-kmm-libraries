package com.compose.kmplibs.data.entity

import com.compose.kmplibs.data.model.MovieResponse

data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
)

fun MovieResponse.map(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl, // TODO: Componer con BaseUrl
    )
}
package com.compose.kmplibs.data.remote.response

import com.compose.kmplibs.data.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    val page: Int,
    val results: List<MovieResponse>
)

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterUrl: String,
)

fun MovieResponse.toModel() =
    Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = title
    )
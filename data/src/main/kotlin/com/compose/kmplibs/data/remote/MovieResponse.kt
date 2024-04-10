package com.compose.kmplibs.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterUrl: String,
    @SerialName("adult") val adult: Boolean,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("tagline") val tagline: String,
    @SerialName("runtime") val runtime: Int,
    @SerialName("genres") val genres: List<GenreResponse>,
)

@Serializable
data class GenreResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)
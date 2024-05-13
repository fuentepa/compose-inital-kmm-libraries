package com.compose.kmplibs.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterUrl: String,
    @SerialName("backdrop_path") val backdropUrl: String,
    val adult: Boolean,
    @SerialName("release_date") val releaseDate: String,
    val popularity: Float,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int,
    val tagline: String,
    val runtime: Int,
    val genres: List<GenreResponse>,
)

@Serializable
data class GenreResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)
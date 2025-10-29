package com.compose.kmplibs.data.remote.response

import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.model.MovieDetail
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

fun MovieDetailResponse.toModel() =
    MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = title,
        backdropUrl = backdropUrl,
        adult = adult,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        tagline = tagline,
        runtime = runtime,
        genres = genres.map { it.name }
    )
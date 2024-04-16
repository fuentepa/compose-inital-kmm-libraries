package com.compose.kmplibs.data.entity

import com.compose.kmplibs.data.model.MovieDetailResponse

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val adult: Boolean,
    val releaseDate: String,
    val popularity: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val tagline: String,
    val runtime: Int,
    val genres: List<String>
)

fun MovieDetailResponse.map(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl, // Componer con BaseUrl
        adult = adult,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        tagline = tagline,
        runtime = runtime,
        genres = genres.map { it.name }
    )
}
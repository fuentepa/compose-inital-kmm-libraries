package com.compose.kmplibs.data.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val posterUrlDescription: String,
    val backdropUrl: String,
    val adult: Boolean,
    val releaseDate: String,
    val popularity: Float,
    val voteAverage: Float,
    val voteCount: Int,
    val tagline: String,
    val runtime: Int,
    val genres: List<String>,
)

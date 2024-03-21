package com.compose.kmplibs.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmEntity(
    @SerialName("characters") val characters: List<String>,
    @SerialName("director") val director: String,
    @SerialName("episode_id") val episodeId: Int,
    @SerialName("opening_crawl") val openingCrawl: String,
    @SerialName("planets") val planets: List<String>,
    @SerialName("producer") val producer: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("species") val species: List<String>,
    @SerialName("starships") val starships: List<String>,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("vehicles") val vehicles: List<String>
)

package com.compose.data.entity

import com.compose.data.remote.FilmEntity
import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val characters: List<String>,
    val director: String,
    val episodeId: Int,
    val openingCrawl: String,
    val planets: List<String>,
    val producer: String,
    val releaseDate: String,
    val species: List<String>,
    val starships: List<String>,
    val title: String,
    val url: String,
    val vehicles: List<String>
)

fun FilmEntity.map() = Film(
    characters = characters,
    director = director,
    episodeId = episodeId,
    openingCrawl = openingCrawl,
    planets = planets,
    producer = producer,
    releaseDate = releaseDate,
    species = species,
    starships = starships,
    title = title,
    url = url,
    vehicles = vehicles
)

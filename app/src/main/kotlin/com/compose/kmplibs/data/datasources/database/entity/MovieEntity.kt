package com.compose.kmplibs.data.datasources.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.remote.response.MovieResponse

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val posterUrlDescription: String
)

// Funciones de mapeo
fun MovieEntity.toModel(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = posterUrlDescription
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = posterUrlDescription
    )
}

fun MovieResponse.toEntity() =
    MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = title
    )

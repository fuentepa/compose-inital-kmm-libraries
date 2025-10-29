package com.compose.kmplibs.data.datasources.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.compose.kmplibs.data.datasources.database.converter.StringListConverter
import com.compose.kmplibs.data.model.MovieDetail
import com.compose.kmplibs.data.remote.response.MovieDetailResponse

@Entity(tableName = "movie_details")
@TypeConverters(StringListConverter::class)
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
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
    val genres: List<String>
)

// Funciones de mapeo
fun MovieDetailEntity.toModel(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = posterUrlDescription,
        backdropUrl = backdropUrl,
        adult = adult,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        tagline = tagline,
        runtime = runtime,
        genres = genres
    )
}

fun MovieDetail.toEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        posterUrlDescription = posterUrlDescription,
        backdropUrl = backdropUrl,
        adult = adult,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        tagline = tagline,
        runtime = runtime,
        genres = genres
    )
}

fun MovieDetailResponse.toEntity(): MovieDetailEntity {
    return MovieDetailEntity(
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
}
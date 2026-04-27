package com.compose.kmplibs.data.datasources.database

import com.compose.kmplibs.data.remote.TMDBApiService
import com.compose.kmplibs.data.remote.response.MovieDetailResponse
import com.compose.kmplibs.data.remote.response.MovieResponse
import com.compose.kmplibs.data.source.RemoteDataSource
import com.compose.kmplibs.data.utils.catching
import org.koin.core.annotation.Single

@Single(createdAtStart = true, binds = [RemoteDataSource::class])
class RemoteDataSourceImpl(
    private val apiService: TMDBApiService
) : RemoteDataSource {

    override suspend fun getTopRatedMovies(): Result<List<MovieResponse>> = catching {
        apiService.getTopRatedMovies().results
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetailResponse> = catching {
        apiService.getMovieDetail(id)
    }
}

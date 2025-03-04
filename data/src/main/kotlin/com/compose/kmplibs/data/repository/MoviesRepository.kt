package com.compose.kmplibs.data.repository

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result

import com.compose.kmplibs.data.source.LocalDataSource
import com.compose.kmplibs.data.source.RemoteDataSource
import org.koin.core.annotation.Single


@Single
class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getTopRatedMovies(): Result<List<Movie>>  {
        if (localDataSource.isEmpty()) {
            val remoteResult: Result<List<Movie>> = remoteDataSource.getTopRatedMovies()

            remoteResult.fold(
                {
                    return remoteResult
                },
                { movies ->
                    localDataSource.saveTopRatedMovies(movies)
                }
            )
        }

        return localDataSource.getTopRatedMovies()
    }

    suspend fun getMovieDetails(id: Int): Result<MovieDetail> {

        val localResult = localDataSource.getMovieDetails(id)

        localResult.fold(
            { _ ->
                val remoteResult: Result<MovieDetail> = remoteDataSource.getMovieDetails(id)
                
                remoteResult.fold({},
                    { movieDetail ->
                        localDataSource.saveMovieDetail(movieDetail)
                    }
                )

                return remoteResult
            },
            { movieDetail ->
                return localResult
            }
        )
    }

}
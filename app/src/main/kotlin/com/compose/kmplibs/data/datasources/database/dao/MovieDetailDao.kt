package com.compose.kmplibs.data.datasources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.kmplibs.data.datasources.database.entity.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity?
    
    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    fun getMovieDetailFlow(movieId: Int): Flow<MovieDetailEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)
    
    @Query("DELETE FROM movie_details WHERE id = :movieId")
    suspend fun deleteMovieDetail(movieId: Int)
} 
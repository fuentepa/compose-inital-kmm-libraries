package com.compose.kmplibs.data.datasources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.kmplibs.data.datasources.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMoviesFlow(): Flow<List<MovieEntity>>
    
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)
    
    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
    
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?
} 
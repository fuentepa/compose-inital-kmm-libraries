package com.compose.kmplibs.data.datasources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compose.kmplibs.data.datasources.database.dao.MovieDao
import com.compose.kmplibs.data.datasources.database.dao.MovieDetailDao
import com.compose.kmplibs.data.datasources.database.entity.MovieDetailEntity
import com.compose.kmplibs.data.datasources.database.entity.MovieEntity


@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class],
    version = 1,
    exportSchema = false  //true para exportarlo
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
}
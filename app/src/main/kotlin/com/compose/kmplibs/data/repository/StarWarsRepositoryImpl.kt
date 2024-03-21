package com.compose.kmplibs.data.repository

import android.util.Log
import com.compose.kmplibs.data.entity.Film
import com.compose.kmplibs.data.entity.map
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.remote.StarWarsApiService
import com.compose.kmplibs.data.remote.tryCall
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class StarWarsRepositoryImpl(@InjectedParam private val starWarsApiService: StarWarsApiService) : StarWarsRepository {

    override suspend fun getAllfilms(): Result<List<Film>> = tryCall {
        Log.d("StarWarsRepositoryImpl", "->  trycall -> getAllfilms")
        starWarsApiService.getAllfilms().map { it.map() }
    }

}
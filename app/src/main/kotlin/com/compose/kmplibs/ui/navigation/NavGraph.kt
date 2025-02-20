package com.compose.kmplibs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.compose.kmplibs.ui.screens.movieDetails.MovieDetailScreen
import com.compose.kmplibs.ui.screens.movies.MoviesScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            MoviesScreen(
                { movieId ->
                    navController.navigate(Detail(movieId))
                }
            )
        }
        
        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            MovieDetailScreen(
                movieId = detail.moviId,
                onBack = { navController.popBackStack() }
            )
        }
    }
} 
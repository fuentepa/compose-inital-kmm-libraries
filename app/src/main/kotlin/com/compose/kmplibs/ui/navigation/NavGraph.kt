package com.compose.kmplibs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.compose.kmplibs.ui.screens.movieDetails.MovieDetailScreen
import com.compose.kmplibs.ui.screens.movies.MoviesScreen

@Composable
fun navHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MOVIES
    ) {
        composable(NavRoutes.MOVIES) { MoviesScreen(navController) }
        
        composable(
            route = NavRoutes.MOVIE_DETAIL,
            arguments = listOf( navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen( movieId = movieId, navController = navController )}
    }
} 
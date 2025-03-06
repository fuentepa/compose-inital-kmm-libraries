package com.compose.kmplibs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.compose.kmplibs.ui.screens.movieDetails.MovieDetailScreen
import com.compose.kmplibs.ui.screens.movies.MoviesScreen
import com.compose.kmplibs.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    navDestination: NavDestinations = NavDestinations.Home
) {
    NavHost(
        navController = navController,
        startDestination = navDestination
    ) {
        composable<NavDestinations.Home> {
            MoviesScreen(
                onMovieClick = { movieId ->
                    navController.navigate(NavDestinations.Detail(movieId))
                }
            )
        }
        
        composable<NavDestinations.Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<NavDestinations.Detail>()
            MovieDetailScreen(
                movieId = detail.moviId,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable<NavDestinations.Settings> {
            SettingsScreen()
        }
    }
} 
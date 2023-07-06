package com.andreformosa.moviedb

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreformosa.moviedb.Destinations.HOME_ROUTE
import com.andreformosa.moviedb.feature.home.HomeScreen

object Destinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun MovieDBNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(HOME_ROUTE) {
            HomeScreen()
        }
    }
}

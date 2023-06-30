package com.brz.beersearch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brz.beersearch.ui.screen.BeerDetail
import com.brz.beersearch.ui.screen.MainScreen

@Composable
fun NavigationHost(
    navController : NavHostController
){
    NavHost(navController = navController, startDestination = Route.MainScreen.route){
        composable(Route.MainScreen.route){
            MainScreen(navController)
        }
        composable(Route.BeerDetail.route){
            BeerDetail(id= it.arguments?.getString("id")?:"0")
        }
    }
}
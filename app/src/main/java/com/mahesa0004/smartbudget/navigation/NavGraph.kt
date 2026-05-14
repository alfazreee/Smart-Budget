package com.mahesa0004.smartbudget.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahesa0004.smartbudget.screen.BiayaBulananScreen
import com.mahesa0004.smartbudget.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.BiayaBulanan.route) {
            BiayaBulananScreen(navController)
        }
    }
}
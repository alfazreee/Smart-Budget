package com.mahesa0004.smartbudget.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahesa0004.smartbudget.screen.BiayaBulananScreen
import com.mahesa0004.smartbudget.screen.MainScreen
import com.mahesa0004.smartbudget.screen.TambahPengeluaranScreen
import com.mahesa0004.smartbudget.screen.UbahPengeluaranScreen

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
        composable(route = Screen.TambahPengeluaran.route) {
            TambahPengeluaranScreen(navController)
        }
        composable(
            route = Screen.UbahPengeluaran.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
            UbahPengeluaranScreen(navController = navController, id = id)
        }
    }
}
package com.mahesa0004.smartbudget.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object BiayaBulanan : Screen("biayaBulananScreen")
}
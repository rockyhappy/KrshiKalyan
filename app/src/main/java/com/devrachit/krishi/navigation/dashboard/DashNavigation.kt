package com.devrachit.krishi.navigation.dashboard

sealed class DashScreens(val route: String) {
    object MainScreen : DashScreens("MainScreen")
}
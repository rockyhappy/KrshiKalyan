package com.devrachit.krishi.navigation.dashboard

sealed class DashScreens(val route: String) {
    object MainScreen : DashScreens("MainScreen")

    object MainScreenBorrower : DashScreens("MainScreenBorrower")

    object AddProductScreen : DashScreens("AddProductScreen")

    object MyBorrowersScreen : DashScreens("MyBorrowersScreen")

    object ReviewScreen : DashScreens("ReviewScreen")

    object ProductDetailScreen : DashScreens("ProductDetailScreen")

    object MadeRequestScreen : DashScreens("madeRequestScreen")
}
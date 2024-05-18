package com.devrachit.krishi.navigation.auth

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devrachit.krishi.navigation.dashboard.DashScreens
import com.devrachit.krishi.presentation.authScreens.OtpScreen.OtpScreen
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.LanguageChoiceScreen
import com.devrachit.krishi.presentation.authScreens.loginScreen.loginScreen
import com.devrachit.krishi.presentation.authScreens.signupScreen.registerScreen
import com.devrachit.krishi.presentation.authScreens.splashScreen.SplashScreen
import com.devrachit.krishi.presentation.dashboardScreens.addProduct.AddProductScreen
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.MainScreenLender
import com.devrachit.krishi.presentation.dashboardScreens.mainScreenBorrower.MainScreenBorrower
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.MyBorrowersScreen
import com.devrachit.krishi.presentation.dashboardScreens.reviewScreen.ReviewScreen

@ExperimentalMaterial3Api
@Composable
fun authNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AuthScreens.SplashScreen.route
    ) {
        composable(AuthScreens.LoginScreen.route) {
            loginScreen(navController = navHostController)
        }
        composable(AuthScreens.RegisterScreen.route) {
            registerScreen(navController = navHostController)
        }
        composable(AuthScreens.LanguageChoiceScreen.route) {
            LanguageChoiceScreen(navController = navHostController)
        }
        composable(AuthScreens.OtpScreen.route) {
            OtpScreen(navController= navHostController)
        }
        composable(DashScreens.MainScreen.route) {
            MainScreenLender(navController = navHostController)
        }
        composable(DashScreens.MainScreenBorrower.route){
            MainScreenBorrower(navController = navHostController)
        }
        composable(AuthScreens.SplashScreen.route){
            SplashScreen(navController = navHostController)
        }
        composable(DashScreens.AddProductScreen.route){
            AddProductScreen(navController = navHostController)
        }
        composable(DashScreens.ReviewScreen.route){
            ReviewScreen(navController = navHostController)
        }
        composable(DashScreens.MyBorrowersScreen.route){
            MyBorrowersScreen(navController = navHostController)
        }

    }
}
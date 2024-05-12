package com.devrachit.krishi.navigation.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devrachit.krishi.navigation.auth.AuthScreens
import com.devrachit.krishi.presentation.authScreens.OtpScreen.OtpScreen
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.LanguageChoiceScreen
import com.devrachit.krishi.presentation.authScreens.loginScreen.loginScreen
import com.devrachit.krishi.presentation.authScreens.signupScreen.registerScreen
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.MainScreen
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.MainScreenLender

@ExperimentalMaterial3Api
@Composable
fun DashNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = DashScreens.MainScreen.route
    ) {
        composable(DashScreens.MainScreen.route) {
            MainScreenLender(navController = navHostController)
        }
    }
}
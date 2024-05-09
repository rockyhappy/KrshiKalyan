package com.devrachit.krishi.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devrachit.krishi.presentation.authScreens.OtpScreen.OtpScreen
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.LanguageChoiceScreen
import com.devrachit.krishi.presentation.authScreens.loginScreen.loginScreen
import com.devrachit.krishi.presentation.authScreens.signupScreen.registerScreen

@ExperimentalMaterial3Api
@Composable
fun authNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AuthScreens.RegisterScreen.route
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
    }
}
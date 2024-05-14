package com.devrachit.krishi.navigation.auth

sealed class AuthScreens(val route: String) {
    object LoginScreen : AuthScreens("LoginScreen")

    object RegisterScreen : AuthScreens("RegisterScreen")

    object LanguageChoiceScreen : AuthScreens("LanguageChoiceScreen")

    object OtpScreen : AuthScreens("OtpScreen")

    object SplashScreen : AuthScreens("SplashScreen")
}
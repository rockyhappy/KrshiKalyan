package com.devrachit.krishi.navigation

sealed class AuthScreens(val route: String) {
    object LoginScreen : AuthScreens("LoginScreen")

    object RegisterScreen : AuthScreens("RegisterScreen")

    object LanguageChoiceScreen : AuthScreens("LanguageChoiceScreen")

    object OtpScreen : AuthScreens("OtpScreen")
}
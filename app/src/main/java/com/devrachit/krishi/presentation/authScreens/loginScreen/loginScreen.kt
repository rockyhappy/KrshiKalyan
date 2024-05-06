package com.devrachit.krishi.presentation.authScreens.loginScreen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun loginScreen( navController: NavController) {
    val viewModel : LoginViewModel= hiltViewModel()
    Text(
        text ="Login Screen",
        modifier = Modifier
            .clickable {
                viewModel.click("Hindi")
                navController.navigate("RegisterScreen") {
                    popUpTo("LoginScreen") {
                        inclusive = true
                    }
                }
            }
    )
}
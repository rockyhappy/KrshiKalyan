package com.devrachit.krishi.presentation.authScreens.signupScreen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun registerScreen(navController: NavController) {
    val viewModel: SignupViewModel = hiltViewModel()
    Text(
        text =viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value ,
        modifier = Modifier.clickable {
            navController.navigate("LoginScreen") {
                popUpTo("RegisterScreen") {
                    inclusive = true
                }
            }
        }
    )
}
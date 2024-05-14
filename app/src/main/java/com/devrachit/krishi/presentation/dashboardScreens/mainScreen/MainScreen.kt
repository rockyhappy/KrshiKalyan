package com.devrachit.krishi.presentation.dashboardScreens.mainScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MainScreenLender(navController: NavController)
{
    val viewModel : MainScreenViewModel = hiltViewModel()
    Text(text = "Main Screen Lender",color= Color.Black,fontSize=30.sp)
}
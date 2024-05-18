package com.devrachit.krishi.presentation.dashboardScreens.mainScreenBorrower

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.MainScreenViewModel

@Composable
fun MainScreenBorrower(navController: NavController)
{
    val viewModel : MainScreenViewModel = hiltViewModel()
    Text(text = "Main Screen Borrower",color= Color.Black,fontSize=30.sp)
}
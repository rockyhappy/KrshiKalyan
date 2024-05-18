package com.devrachit.krishi.presentation.dashboardScreens.addProduct

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AddProductScreen(navController: NavController) {
    val viewModel: AddProductViewModel = hiltViewModel()
    Text(text="Add Product Screen",color= Color.Black, fontSize = 100.sp)
}
package com.devrachit.krishi.presentation.dashboardScreens.productDetailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProductDetailScreen(navController: NavController) {
    Text(text ="Product Detail Screen", color = Color.Black, fontSize = 100.sp)
}
package com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily


@Composable
fun Heading(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontFamily = customFontFamily
    )
}
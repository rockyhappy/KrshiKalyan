package com.devrachit.krishi.presentation.authScreens.signupScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.ui.theme.errorColor

@Composable
fun errorFeild(text: String , modifier: Modifier) {
    Text(
        text = text,
        color = errorColor,
        fontFamily = customFontFamily,
        fontSize = 12.sp,
        modifier = modifier.padding(start = 30.dp)
    )
}
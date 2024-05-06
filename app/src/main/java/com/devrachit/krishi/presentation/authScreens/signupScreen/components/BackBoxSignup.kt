package com.devrachit.krishi.presentation.authScreens.signupScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun BackBoxSignup2()
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Color.White)
            .zIndex(0f)

    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
                .background(primaryVariantColor1)
                .zIndex(0f)
        )

    }
}
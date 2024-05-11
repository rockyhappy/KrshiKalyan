package com.devrachit.krishi.presentation.authScreens.loginScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun DialogContent() {
    Box(
        modifier = Modifier
            .size(76.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center),
            color = primaryVariantColor1
        )
    }
}
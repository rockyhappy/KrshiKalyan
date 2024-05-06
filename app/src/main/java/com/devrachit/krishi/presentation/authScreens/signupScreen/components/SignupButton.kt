package com.devrachit.krishi.presentation.authScreens.signupScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun SignupButton(text: String, onClick: () -> Unit, modifier : Modifier = Modifier) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.White),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Black,
            containerColor = primaryVariantColor1
        ),
        border = ButtonDefaults.outlinedButtonBorder

    ) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = customFontFamily,
            fontSize = 16.sp
        )
    }
}
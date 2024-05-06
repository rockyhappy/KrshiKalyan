package com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun LanguageButton(text: String, onClick: () -> Unit, modifier : Modifier = Modifier) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.White)
            ,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Black,
        ),
        border= ButtonDefaults.outlinedButtonBorder
    ) {
        Text(
            text = text,
            color = primaryVariantColor1,
            fontFamily = customFontFamily,
            fontSize = 16.sp
        )
    }

}

@Preview
@Composable
fun LanguageButtonPreview() {
    LanguageButton(text = "English", onClick = {})
}
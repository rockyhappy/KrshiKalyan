package com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily

@Composable
fun Heading(
    heading: String ,
    modifier: Modifier = Modifier,
    maxLines: Int = 2,
    overflow: TextOverflow = TextOverflow.Visible
) {
    Text(
        text = heading,
        color = Color.White,
        fontSize = 40.sp,
        modifier = modifier,
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Bold,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Center
    )
}
@Preview
@Composable
fun HeadingPreview() {
    Heading(heading = "Language Choice Screen")
}
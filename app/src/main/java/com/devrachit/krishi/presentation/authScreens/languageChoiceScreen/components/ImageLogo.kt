package com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.devrachit.krishi.R

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.peasant),
        contentDescription =null,
        modifier=modifier
        )
}
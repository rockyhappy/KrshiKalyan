package com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devrachit.krishi.R

@Composable
fun NavigationDrawerHeader() {
    Box(modifier = Modifier
        .width(260.dp)
        .height(200.dp)
        .background(Color.White)
        ,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.peasant),
            contentDescription = null,
            modifier = Modifier
                .height(240.dp)
                .width(240.dp)
        )
    }
}
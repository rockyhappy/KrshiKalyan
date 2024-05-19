package com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DrawerItem(text:String, onClick:()->Unit, Icon : ImageVector, visible:Boolean=true){
    if(visible) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(260.dp)
                .height(50.dp)
                .background(Color.White),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),

            )
        {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp).fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icon,
                    contentDescription = "Menu",
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = text,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }

        }
    }
}
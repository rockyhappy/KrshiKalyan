package com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun ProductCard(itemModel: itemModel) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(250.dp)
            .background(color = Color.White)
            .clip(RoundedCornerShape(16.dp))
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(26.dp))
                .background(color = Color.White)
                .border(2.dp, primaryVariantColor1,shape= RoundedCornerShape(26.dp))
        )
        {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            )
            {
                CommonImage(
                    data = itemModel.imageUrl,
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .height(100.dp)
                        .width(100.dp)
                )
                Column {
                    Text(
                        text = itemModel.name,
                        modifier = Modifier.padding(start = 20.dp, end = 10.dp),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = customFontFamily
                    )
                    Text(
                        text = "Owner Name: " + itemModel.ownerName,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = customFontFamily
                    )
                }
            }
            Text(
                text = "Price Per Day:  ₹ " + itemModel.price,
                modifier = Modifier.padding(top = 140.dp, start = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = customFontFamily
            )
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(start=20.dp,end=30.dp)
            ){
                Text(
                    text = "Rating: ${itemModel.rating} ★",
                    modifier = Modifier.padding(top = 200.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = customFontFamily
                )
                Icon(imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete Icon",
                    modifier = Modifier.padding(top = 200.dp, start = 20.dp))
            }

        }
    }
}
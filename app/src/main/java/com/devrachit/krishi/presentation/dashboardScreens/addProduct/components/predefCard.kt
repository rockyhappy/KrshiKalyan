package com.devrachit.krishi.presentation.dashboardScreens.addProduct.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.CommonImage
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.CustomButton2
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.ProductCard
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun PredefCard(imageUrl:String,name:String , modifier:Modifier) {

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.White)
            .clip(RoundedCornerShape(16.dp))
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(26.dp))
                .background(color = Color.White)
                .border(2.dp, primaryVariantColor1, shape = RoundedCornerShape(26.dp))
        )
        {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            )
            {
                CommonImage(
                    data = imageUrl,
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(26.dp))
                        .height(150.dp)
                        .width(150.dp)
                )
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = name,
                        modifier = Modifier.padding(start = 20.dp, end = 10.dp),
                        fontSize = 25.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = customFontFamily
                    )
                }
            }
        }
    }
}


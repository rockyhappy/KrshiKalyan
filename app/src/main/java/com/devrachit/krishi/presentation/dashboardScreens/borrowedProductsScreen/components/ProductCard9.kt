package com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import com.devrachit.krishi.domain.models.itemModel2
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.CommonImage
import com.devrachit.krishi.presentation.dashboardScreens.mainScreenBorrower.components.CustomButton
import com.devrachit.krishi.presentation.dashboardScreens.myRequestsScreen.component.CustomButton3
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun ProductCard9(itemModel: itemModel2, onRequestBooking: (itemModel2) -> Unit , onItemClick:(itemModel2) -> Unit){
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(220.dp)
            .background(color = Color.White)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onItemClick(itemModel) }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.White)
                .border(2.dp, primaryVariantColor1, shape = RoundedCornerShape(16.dp))
                .clickable { onItemClick(itemModel) }
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
                        .clip(RoundedCornerShape(16.dp))
                        .height(130.dp)
                        .width(100.dp)
                )
                Column {
                    Text(
                        text = itemModel.name,
                        modifier = Modifier.padding(start = 20.dp, end = 10.dp),
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = customFontFamily
                    )
                    Text(
                        text = "Owner Name: " + itemModel.ownerName,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = customFontFamily
                    )
                }
            }
            Text(
                text = "₹ " + itemModel.price+" /day",
                modifier = Modifier.padding(top = 70.dp, start = 135.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = customFontFamily
            )
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 135.dp)
            ){
                Text(
                    text = "${itemModel.rating} ★",
                    modifier = Modifier.padding(top = 90.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = customFontFamily
                )
            }
            Text(
                text = "Quantity: "+"${itemModel.quantity} units",
                modifier = Modifier.padding(top = 110.dp, start=135.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = customFontFamily
            )
            Text(
                text = "Days: "+"${itemModel.quantity} units",
                modifier = Modifier.padding(top = 130.dp, start=135.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = customFontFamily
            )
            CustomButton3(text = "Review", onClick = { onRequestBooking.invoke(itemModel)}, modifier=Modifier.padding(top= 160.dp, start = 135.dp))
        }
    }
}

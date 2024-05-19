package com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.devrachit.krishi.domain.models.userModel

@Composable
fun DialogCon(isShowingDialog: Boolean, userDetails: userModel, onDismissRequest: () -> Unit) {
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            content = {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(280.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                        ){
                            Text(text = "Borrower Details", color = Color.Black, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            Text(text = "Name: ${userDetails.name}", fontSize = 12.sp)
                            Text(text = "Temporary Address: ${userDetails.tempAddress}", fontSize = 12.sp)
                            Text(text = "Permanent Address: ${userDetails.permAddress}", fontSize = 12.sp)
                            Text(text = "Phone Number: ${userDetails.number}", fontSize = 12.sp)
                            Text(text = "Identification: ${userDetails.identificationType}", fontSize = 12.sp)
                            Text(text = "Identification Number: ${userDetails.identificationNumber}", fontSize = 12.sp)
                        }
                    }
                }

            },
        )
    }
}
package com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.RatingBar
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun ReviewDialog(
    isShowingDialog: Boolean,
    onDismissRequest: () -> Unit,
    onSubmitReview: (String, Int) -> Unit
) {
    val reviewText = remember { mutableStateOf("") }
    val rating = remember { mutableStateOf(0) }
    if (isShowingDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Submit Review",color=Color.Black) },
            text = {
                Column {

                    OutlinedTextField(
                        value = reviewText.value,
                        onValueChange = { reviewText.value = it },
                        label = { Text(text = "Your Review") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RatingBar(
                        rating = rating.value,
                        onRatingChanged = { rating.value = it }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onSubmitReview(reviewText.value, rating.value)
                    onDismissRequest()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryVariantColor1)) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(onClick = onDismissRequest,colors = ButtonDefaults.buttonColors(containerColor = primaryVariantColor1)) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White
        )
    }
}
@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row {
        (1..5).forEach { star ->
            Icon(
                imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChanged(star) }
            )
        }
    }
}


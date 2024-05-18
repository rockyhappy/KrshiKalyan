package com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devrachit.krishi.domain.models.userModel

@Composable
fun DialogContent(
    isShowingDialog: Boolean,
    userDetails: userModel,
    onDismissRequest: () -> Unit
) {
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
        ) {
            Column{
                Text(text ="Name: ${userDetails.name}")
                Text(text ="Phone: ${userDetails.number}")
                Text(text ="Email: ${userDetails.permAddress}")
                Text(text ="Email: ${userDetails.tempAddress}")
                Text(text ="Email: ${userDetails.identificationNumber}/n ${userDetails.identificationType}")
            }

        }
    }
}
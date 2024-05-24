package com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onConfirm) {
                androidx.compose.material3.Text("Log Out", color=Color.Black)
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                androidx.compose.material3.Text("Cancel",color=Color.Black)
            }
        },
        title = {
            androidx.compose.material3.Text(text = "Log Out",color=Color.Black)
        },
        text = {
            androidx.compose.material3.Text("Are you sure you want to log out?",color=Color.Black)
        },
        containerColor = Color.White
    )
}

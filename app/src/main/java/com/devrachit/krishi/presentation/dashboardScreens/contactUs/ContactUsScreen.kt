package com.devrachit.krishi.presentation.dashboardScreens.contactUs

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.SignupButton
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading

@Composable
fun ContactUsScreen(navController: NavController) {
    val viewModel : ContactUsScreenViewModel = hiltViewModel()
    val context= LocalContext.current
    val onEmailClick: () -> Unit = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:rachit22153019@akgec.ac.in")
            putExtra(Intent.EXTRA_SUBJECT, "Customer Support")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
    val onCallUsClick:()->Unit = {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+917534015585")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
    Scaffold(
        containerColor = Color.White,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Heading(
                    text =if(viewModel.sharedViewModel.getLanguage()=="English") "Contact Us" else "हमसे संपर्क करें",
                    modifier =Modifier.padding(bottom=30.dp) )

                Text(
                    text = "Address: 1110-FF Sector 5, Wave City, Ghaziabad,  201010",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 30.dp,top=30.dp)
                        .align(Alignment.CenterHorizontally)
                )
                SignupButton(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Call Us" else "हमें कॉल करें",
                    onClick = { onCallUsClick.invoke() }, modifier=Modifier.padding(top=30.dp))
            }
        }
    )
}
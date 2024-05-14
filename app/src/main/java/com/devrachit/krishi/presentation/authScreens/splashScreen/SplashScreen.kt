package com.devrachit.krishi.presentation.authScreens.splashScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.krishi.R
import com.devrachit.krishi.navigation.auth.AuthScreens
import com.devrachit.krishi.navigation.dashboard.DashScreens
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.dashboardScreens.DashboardActivity
import com.devrachit.krishi.ui.theme.primaryVariantColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val viewModel: SplashScreenViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = primaryVariantColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        )
        {


            Image(
                painter = painterResource(id = R.drawable.bg_splash),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .padding(top = 200.dp)
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.TopCenter
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.peasant),
                    contentDescription = null,
                    modifier = Modifier
                        .height(240.dp)
                        .width(240.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.font1)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(top = 280.dp)
                )
            }


        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.getUserData()
    }
    val selected = viewModel.selected.collectAsStateWithLifecycle()

    if (selected.value == 1) {
        navController.navigate(AuthScreens.LanguageChoiceScreen.route) {
            popUpTo(AuthScreens.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    if (selected.value == 2) {
        if(viewModel.sharedViewModel.getUser().isBorrower)
        {
            navController.navigate(DashScreens.MainScreenBorrower.route) {
                popUpTo(AuthScreens.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
        else
        {
            navController.navigate(DashScreens.MainScreen.route) {
                popUpTo(AuthScreens.SplashScreen.route) {
                    inclusive = true
                }
            }
        }

    }

}

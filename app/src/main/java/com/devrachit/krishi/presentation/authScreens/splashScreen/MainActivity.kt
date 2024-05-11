package com.devrachit.krishi.presentation.authScreens.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.devrachit.krishi.R
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.dashboardScreens.DashboardActivity
import com.devrachit.krishi.ui.theme.KrishiTheme
import com.devrachit.krishi.ui.theme.primaryVariantColor
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            window.statusBarColor = primaryVariantColor1.toArgb()
//            window.navigationBarColor = primaryVariantColor1.toArgb()
//            WindowCompat.setDecorFitsSystemWindows(window, false)
//            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            KrishiTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = primaryVariantColor),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center

                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                    {


                        Image(
                            painter = painterResource(id = R.drawable.bg_splash),
                            modifier=Modifier.fillMaxSize(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Box(modifier=Modifier.padding(top=200.dp)
                            .align(Alignment.TopCenter),
                            contentAlignment = Alignment.TopCenter)
                        {
                            Image(
                                painter = painterResource(id = R.drawable.peasant),
                                contentDescription = null,
                                modifier=Modifier
                                    .height(240.dp)
                                    .width(240.dp))
                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = Color.White,
                                fontSize = 40.sp,
                                fontFamily = FontFamily(Font(R.font.font1)),
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                                modifier = Modifier.padding(top=280.dp)
                            )
                        }


                    }
                }
            }
            LaunchedEffect(key1 = true)
            {
                delay(1000)
                val auth = Firebase.auth
                if(auth.currentUser!=null)
                {
                    val intent = Intent(this@MainActivity, Auth::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    val intent = Intent(this@MainActivity, Auth::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }
}


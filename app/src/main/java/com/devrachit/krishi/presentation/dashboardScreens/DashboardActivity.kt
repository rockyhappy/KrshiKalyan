package com.devrachit.krishi.presentation.dashboardScreens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.devrachit.krishi.R
import com.devrachit.krishi.datastore.readFromDataStore
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.navigation.dashboard.DashNavHost
import com.devrachit.krishi.navigation.dashboard.DashScreens
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.ui.theme.KrishiTheme
import com.devrachit.krishi.ui.theme.primaryVariantColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterial3Api
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = primaryVariantColor.toArgb()
            window.navigationBarColor = primaryVariantColor.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            KrishiTheme {
                val navController = rememberNavController()
                val dataStore: DataStore<Preferences> = this.createDataStore(name = "user")
                var some =false
                LaunchedEffect(true) {
                    some = readFromDataStore(dataStore,"borrower").toBoolean()

                }
                if(some)
                {
                    DashNavHost(navHostController =navController,DashScreens.MainScreenBorrower.route)
                }
                else{
                    DashNavHost(navHostController =navController,DashScreens.MainScreen.route)
                }

            }

        }
    }
}


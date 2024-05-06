package com.devrachit.krishi.presentation.authScreens.signupScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devrachit.krishi.datastore.SaveToDataStore
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.GoButton
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.Heading
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.ImageLogo
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.LanguageButton
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.backBox
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.BackBoxSignup2

@Composable
fun registerScreen(navController: NavController) {
    val viewModel: SignupViewModel = hiltViewModel()
    val data : SaveToDataStore = hiltViewModel()

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxHeight()
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Log.d("SignUpScreen", it.toString())
            BackBoxSignup2()
            Heading(
                heading = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "SignUp" else "साइन अप",
                modifier = Modifier
                    .padding(top = 76.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
            ImageLogo(modifier= Modifier
                .padding(top = 190.dp)
                .align(Alignment.TopCenter))

        }
    }
}
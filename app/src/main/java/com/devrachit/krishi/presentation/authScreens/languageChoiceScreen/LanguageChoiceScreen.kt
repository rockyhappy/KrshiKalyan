package com.devrachit.krishi.presentation.authScreens.languageChoiceScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.devrachit.krishi.datastore.SaveToDataStore
import com.devrachit.krishi.datastore.readFromDataStore
import com.devrachit.krishi.navigation.AuthScreens
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.GoButton
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.Heading
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.ImageLogo
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.LanguageButton
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.backBox
import kotlinx.coroutines.launch

@Composable
fun LanguageChoiceScreen(navController: NavController) {

    val viewModel: LanguageChoiceViewModel = hiltViewModel()
    val data : SaveToDataStore = hiltViewModel()

    val onEnglishClick: () -> Unit = {
        viewModel.sharedViewModel.setLanguage("English")
        viewModel.setLanguage("English")
    }
    val onHindiClick: () -> Unit = {
        viewModel.sharedViewModel.setLanguage("Hindi")
        viewModel.setLanguage("Hindi")
    }

    val onGoButtonClick : () -> Unit = {
        navController.navigate(AuthScreens.RegisterScreen.route)
    }

    LaunchedEffect(key1 = true) {
        val lang=readFromDataStore(viewModel.dataStore,  "language")
        Log.d("Language", lang?:"")
        if(lang=="English" || lang=="Hindi"){
            viewModel.sharedViewModel.setLanguage(lang)
        }
    }

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
            Log.d("LanguageChoiceScreen", it.toString())
            backBox()
            Heading(
                heading = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Language" else "भाषा",
                modifier = Modifier
                    .padding(top = 76.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
            Heading(
                heading = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Choice" else "चुनाव",
                modifier = Modifier
                    .padding(top = 160.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
            ImageLogo(modifier= Modifier
                .padding(top = 270.dp)
                .align(Alignment.TopCenter))
            LanguageButton(
                text = "English",
                { onEnglishClick() },
                modifier = Modifier.padding(top = 500.dp, start = 16.dp, end = 16.dp)
            )
            LanguageButton(
                text = "हिंदी",
                { onHindiClick() },
                modifier = Modifier.padding(top = 600.dp, start = 16.dp, end = 16.dp)
            )
            GoButton(
                text = if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Begin" else "आरंभ करें",
                onClick = { onGoButtonClick() },
                modifier = Modifier.padding(top = 700.dp, start = 16.dp, end = 16.dp , bottom = 30.dp)
            )
        }
    }
}
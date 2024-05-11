package com.devrachit.krishi.presentation.authScreens.OtpScreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.Heading
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.ImageLogo
import com.devrachit.krishi.presentation.authScreens.loginScreen.LoginViewModel
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.BackBoxSignup2
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SignupButton
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.errorFeild
import com.devrachit.krishi.presentation.dashboardScreens.DashboardActivity
import com.devrachit.krishi.ui.theme.errorColor
import com.devrachit.krishi.ui.theme.gray
import com.devrachit.krishi.ui.theme.primaryVariantColor1
@ExperimentalMaterial3Api
@Composable
fun OtpScreen(navController: NavController) {

    val viewModel: OtpViewModel = hiltViewModel()
    val numberState = remember { mutableStateOf(TextFieldValue())}
    val context = LocalContext.current
    val onGetOtpClicked : () -> Unit = {
        val intent= Intent(context, DashboardActivity::class.java)
        context.startActivity(intent)
//        (context as Auth).finish()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            BackBoxSignup2()
            Heading(
                heading = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "OTP" else "ओटीपी",
                modifier = Modifier
                    .padding(top = 76.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
            ImageLogo(
                modifier = Modifier
                    .padding(top = 190.dp)
                    .align(Alignment.TopCenter)
            )

            OutlinedTextField(
                value = numberState.value,
                onValueChange = { numberState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 500.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Mobile Number" else "मोबाइल नंबर",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.numberValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.numberValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.numberValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.numberValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.numberValid.value) gray else errorColor,
                    focusedTextColor = if (viewModel.numberValid.value) Color.Black else errorColor,
                    unfocusedTextColor = if (viewModel.numberValid.value) Color.Black else errorColor
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            errorFeild(text = "Error Fields", modifier = Modifier.padding(top = 570.dp))
            SignupButton(
                text = if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Login" else "लॉग इन",
                onClick = { onGetOtpClicked()},
                modifier = Modifier
                    .padding(top = 630.dp, start = 16.dp, end = 16.dp, bottom = 190.dp)
                    .fillMaxWidth()
            )


        }
    }
}
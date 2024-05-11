package com.devrachit.krishi.presentation.authScreens.loginScreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.common.util.address
import com.devrachit.krishi.common.util.isLongAndLettersOnly
import com.devrachit.krishi.common.util.isNumbersOnlyAndLengthTen
import com.devrachit.krishi.common.util.isValidOtp
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.navigation.AuthScreens
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.Heading
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.ImageLogo
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.BackBoxSignup2
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SignupButton
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.errorFeild
import com.devrachit.krishi.presentation.dashboardScreens.DashboardActivity
import com.devrachit.krishi.ui.theme.errorColor
import com.devrachit.krishi.ui.theme.gray
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@ExperimentalMaterial3Api
@Composable
fun loginScreen(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val numberState = remember { mutableStateOf(TextFieldValue()) }
    val otpState = remember { mutableStateOf(TextFieldValue()) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var isSignupButtonEnabled by remember { mutableStateOf(false) }
    var timerValue by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val onCreateAccountClick: () -> Unit = {
        navController.navigate(AuthScreens.RegisterScreen.route){
            popUpTo(AuthScreens.LoginScreen.route) {
                inclusive = true
            }
        }
    }

    val onLoginClicked: () -> Unit = {
        var flag=true

        if(otpState.value.text.isValidOtp()) {
            viewModel.otpValid.value = true
        }
        else {
            viewModel.otpValid.value = false
            flag=false
        }
        if(flag==true)
        viewModel.verifyOTP(context,otpState.value.text)
    }

    val sendOtp: () -> Unit = {
        var flag = true
        if (nameState.value.text.isLongAndLettersOnly())
            viewModel.nameValid.value = true
        else {
            viewModel.nameValid.value = false
            flag = false
        }

        if (numberState.value.text.isNumbersOnlyAndLengthTen())
            viewModel.numberValid.value = true
        else {
            viewModel.numberValid.value = false
            flag = false
        }


        if (flag == true) {
            isSignupButtonEnabled = true
            viewModel.sendOTP(context, numberState.value.text.toString())
            viewModel.sharedViewModel.setUser(
                userModel(
                    name = nameState.value.text,
                    number = numberState.value.text,
                    "","","","",true
                )
            )
            isButtonEnabled = false
            isTimerRunning = true
            viewModel.viewModelScope.launch {
                while (timerValue > 0) {
                    delay(1000)
                    timerValue--
                }
                isButtonEnabled = true
                isTimerRunning = false
                timerValue = 60
            }
        }
    }

    LaunchedEffect(viewModel.sharedViewModel.getUserLoggedIn()) {
        if (viewModel.sharedViewModel.getUserLoggedIn()) {
            val intent = Intent(context, DashboardActivity::class.java)
            startActivity(context, intent, null)
            (context as Auth).finish()
        }

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
//            contentAlignment = Alignment.TopCenter
        ) {

            BackBoxSignup2()
            Heading(
                heading = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Login" else "लॉग इन",
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
                value = nameState.value,
                onValueChange = { nameState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 400.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Name" else "नाम",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.nameValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.nameValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.nameValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.nameValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.nameValid.value) gray else errorColor,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            errorFeild(
                text = if (viewModel.nameValid.value) "" else "Recheck",
                modifier = Modifier.padding(top = 470.dp)
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
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            errorFeild(
                text = if (viewModel.numberValid.value) "" else "Recheck",
                modifier = Modifier.padding(top = 570.dp)
            )

            OutlinedTextField(
                value = otpState.value,
                onValueChange = { otpState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 600.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "OTP" else "ओटीपी",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.otpValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.otpValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.otpValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.otpValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.otpValid.value) gray else errorColor,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            errorFeild(
                text = if (viewModel.otpValid.value) "" else "Recheck",
                modifier = Modifier.padding(top = 670.dp)
            )

            SignupButton(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (isTimerRunning) "Resend OTP in $timerValue seconds"
                    else "Get OTP"
                } else {
                    if (isTimerRunning) "सेकंड में ओटीपी पुनः भेजें $timerValue"
                    else "ओटीपी प्राप्त करें"
                },
                onClick = { sendOtp() },
                modifier = Modifier
                    .padding(top = 710.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                enabled = isButtonEnabled
            )

            SignupButton(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Login" else "लॉग इन",
                onClick = { onLoginClicked() },
                modifier = Modifier
                    .padding(top = 810.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                enabled = isSignupButtonEnabled
            )
            Row(modifier = Modifier
                .padding(top = 910.dp, start = 16.dp, end = 16.dp, bottom = 100.dp)
                .fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            )
            {
                Text(
                    text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Create an account? Click Here" else "खाता बनाएं? यहाँ क्लिक करें",
                    modifier = Modifier
                        .wrapContentWidth()
                        .clickable { onCreateAccountClick() },
                    textAlign = TextAlign.Center,
                    fontFamily = customFontFamily,
                    fontSize = 12.sp
                )
            }
            if (loading.value) {
                LoadingDialog(isShowingDialog = true)
            }
            else{
                LoadingDialog(isShowingDialog =false)
            }

        }


    }
}
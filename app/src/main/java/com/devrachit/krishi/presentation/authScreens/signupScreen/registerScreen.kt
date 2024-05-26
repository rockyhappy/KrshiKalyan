package com.devrachit.krishi.presentation.authScreens.signupScreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.common.util.address
import com.devrachit.krishi.common.util.isAadharValid
import com.devrachit.krishi.common.util.isDrivingLicenseValid
import com.devrachit.krishi.common.util.isLongAndLettersOnly
import com.devrachit.krishi.common.util.isNumbersOnlyAndLengthTen
import com.devrachit.krishi.common.util.isPANValid
import com.devrachit.krishi.common.util.isRationNumberValid
import com.devrachit.krishi.common.util.isVoterIdValid
import com.devrachit.krishi.datastore.SaveToDataStore
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.navigation.auth.AuthScreens
import com.devrachit.krishi.navigation.dashboard.DashScreens
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.Heading
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.ImageLogo
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.BackBoxSignup2
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.CustomDropdown
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SignupButton
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SwitchWithIconExample
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.errorFeild
import com.devrachit.krishi.presentation.dashboardScreens.DashboardActivity
import com.devrachit.krishi.ui.theme.errorColor
import com.devrachit.krishi.ui.theme.gray
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@ExperimentalMaterial3Api
@Composable
fun registerScreen(navController: NavController) {
    val viewModel: SignupViewModel = hiltViewModel()
    val data: SaveToDataStore = hiltViewModel()
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val numberState = remember { mutableStateOf(TextFieldValue()) }
    val tempAddressState = remember { mutableStateOf(TextFieldValue()) }
    val permAddressState = remember { mutableStateOf(TextFieldValue()) }
    val identificationNumberState = remember { mutableStateOf(TextFieldValue()) }
    val otpNumberState = remember { mutableStateOf(TextFieldValue()) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var timerValue by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var isSignupButtonEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    var checked by remember { mutableStateOf(true) }
    val options = listOf(
        "Aadhar",
        "Voter ID",
        "Driving License",
        "Ration Number",
        "PAN Card"
    )
    val options1 = listOf(
        "आधार",
        "मतदाता पहचान पत्र",
        "ड्राइविंग लाइसेंस",
        "राशन संख्या",
        "पैन कार्ड"
    )
    var selectedIndex by remember { mutableStateOf(0) }


    val SignupClicked: () -> Unit = {
        viewModel.verifyOTP(otpNumberState.value.text.toString())
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

        if (tempAddressState.value.text.address() && permAddressState.value.text.address()) {
            viewModel.tempAddressValid.value = true
            viewModel.permAddressValid.value = true
        } else {
            viewModel.tempAddressValid.value = false
            viewModel.permAddressValid.value = false
            flag = false
        }
        if(selectedIndex==0 && identificationNumberState.value.text.isAadharValid())
        {
            viewModel.identificationNumberValid.value = true
            flag = true
        }
        else if(selectedIndex==1 && identificationNumberState.value.text.isVoterIdValid())
        {
            viewModel.identificationNumberValid.value = true
            flag = true
        }
        else if(selectedIndex==2 && identificationNumberState.value.text.isDrivingLicenseValid())
        {
            viewModel.identificationNumberValid.value = true
            flag = true
        }
        else if(selectedIndex==3 && identificationNumberState.value.text.isRationNumberValid())
        {
            viewModel.identificationNumberValid.value = true
            flag = true
        }
        else if(selectedIndex==4 && identificationNumberState.value.text.isPANValid())
        {
            viewModel.identificationNumberValid.value = true
            flag=true
        }
        else
        {
            viewModel.identificationNumberValid.value = false
            flag = false
        }
        if (flag == true) {
            isSignupButtonEnabled = true
            viewModel.sendOTP(context, numberState.value.text.toString())
            viewModel.sharedViewModel.setUser(
                userModel(
                    name = nameState.value.text,
                    number = numberState.value.text,
                    tempAddress = tempAddressState.value.text,
                    permAddress = permAddressState.value.text,
                    identificationType = options[selectedIndex],
                    identificationNumber = identificationNumberState.value.text,
                    isBorrower = checked
                )
            )
            isButtonEnabled = false
            isTimerRunning = true
            viewModel.viewModelScope.launch {
                while (timerValue > 0) {
                    delay(1000) // Wait for one second
                    timerValue--
                }
                isButtonEnabled = true
                isTimerRunning = false
                timerValue = 60 // Reset timer value
            }
        }
    }
    val onLoginClicked: () -> Unit = {
        navController.navigate(AuthScreens.LoginScreen.route) {
            popUpTo(AuthScreens.RegisterScreen.route) {
                inclusive = true
            }
        }
    }
    LaunchedEffect(viewModel.sharedViewModel.getUserLoggedIn()) {
        if (viewModel.sharedViewModel.getUserLoggedIn()) {
            if(viewModel.sharedViewModel.getUser().isBorrower)
            {
                navController.navigate(DashScreens.MainScreenBorrower.route){
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
            else
            {
                navController.navigate(DashScreens.MainScreen.route){
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }

        }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            BackBoxSignup2()
            Heading(
                heading = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "SignUp" else "साइन अप",
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
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (viewModel.nameValid.value) "" else "Recheck"
                } else {
                    if (viewModel.nameValid.value) "" else "पुनः जांचें"
                },
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
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (viewModel.numberValid.value) "" else "Recheck"
                } else {
                    if (viewModel.numberValid.value) "" else "पुनः जांचें"
                },
                modifier = Modifier.padding(top = 570.dp)
            )

            OutlinedTextField(
                value = tempAddressState.value,
                onValueChange = { tempAddressState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 600.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Temporary Address" else "अस्थायी पता",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.tempAddressValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.tempAddressValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.tempAddressValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.tempAddressValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.tempAddressValid.value) gray else errorColor,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
            )
            errorFeild(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (viewModel.tempAddressValid.value) "" else "Recheck"
                } else {
                    if (viewModel.tempAddressValid.value) "" else "पुनः जांचें"
                },
                modifier = Modifier.padding(top = 670.dp)
            )

            OutlinedTextField(
                value = permAddressState.value,
                onValueChange = { permAddressState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 700.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Permanent Address" else "स्थायी पता",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.permAddressValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.permAddressValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.permAddressValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.permAddressValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.permAddressValid.value) gray else errorColor,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
            )
            errorFeild(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (viewModel.permAddressValid.value) "" else "Recheck"
                } else {
                    if (viewModel.permAddressValid.value) "" else "पुनः जांचें"
                },
                modifier = Modifier.padding(top = 770.dp)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
            {
                CustomDropdown(
                    options = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") options else options1,
                    selectedIndex = selectedIndex,
                    onSelectedIndexChanged = { selectedIndex = it }
                )
            }

            OutlinedTextField(
                value = identificationNumberState.value,
                onValueChange = { identificationNumberState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 900.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") options[selectedIndex] else options1[selectedIndex] + " " + "नंबर",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.identificationNumberValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.identificationNumberValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.identificationNumberValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.identificationNumberValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.identificationNumberValid.value) gray else errorColor,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
            )
            errorFeild(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (viewModel.identificationNumberValid.value) "" else "Recheck"
                } else {
                    if (viewModel.identificationNumberValid.value) "" else "पुनः जांचें"
                },
                modifier = Modifier.padding(top = 970.dp)
            )

            SwitchWithIconExample(
                modifier = Modifier.padding(top = 1020.dp, start = 30.dp),
                checked = checked,
                onCheckedChange = { checked = it })
            Text(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English")
                    if (checked) "Borrower"
                    else "Lender"
                else
                    if (checked) "उधार लेने वाला"
                    else "उधार देने वाला",
                modifier = Modifier.padding(top = 1030.dp, start = 100.dp),
                fontFamily = customFontFamily,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = otpNumberState.value,
                onValueChange = { otpNumberState.value = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 1100.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "OTP" else "ओटीपी",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.otpNumberState.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.otpNumberState.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.otpNumberState.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.otpNumberState.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.otpNumberState.value) gray else errorColor,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            errorFeild(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") {
                    if (viewModel.otpNumberState.value) "" else "Recheck"
                } else {
                    if (viewModel.otpNumberState.value) "" else "पुनः जांचें"
                }, modifier = Modifier.padding(top = 1170.dp)
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
                    .padding(top = 1210.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                enabled = isButtonEnabled
            )
            SignupButton(
                text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "SignUp" else "साइन अप",
                onClick = { SignupClicked() },
                modifier = Modifier
                    .padding(top = 1310.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),

                enabled = isSignupButtonEnabled
            )

            Row(modifier = Modifier
                .padding(top = 1410.dp, start = 16.dp, end = 16.dp, bottom = 100.dp)
                .fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            )
            {
                Text(
                    text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Already have an account? Click Here" else "पहले से ही खाता है? यहाँ क्लिक करें",
                    modifier = Modifier
                        .wrapContentWidth()
                        .clickable { onLoginClicked() },
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
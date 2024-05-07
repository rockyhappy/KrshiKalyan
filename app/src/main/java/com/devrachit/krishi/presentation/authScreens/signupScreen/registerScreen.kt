package com.devrachit.krishi.presentation.authScreens.signupScreen

import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.datastore.SaveToDataStore
import com.devrachit.krishi.navigation.AuthScreens
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.GoButton
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.Heading
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.ImageLogo
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.LanguageButton
import com.devrachit.krishi.presentation.authScreens.languageChoiceScreen.components.backBox
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.BackBoxSignup2
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.CustomDropdown
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SignupButton
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SwitchWithIconExample
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.clickableWithoutRipple
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.errorFeild
import com.devrachit.krishi.ui.theme.errorColor
import com.devrachit.krishi.ui.theme.gray
import com.devrachit.krishi.ui.theme.primaryVariantColor1

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
    val context = LocalContext.current
    val SignupClicked: () -> Unit = {

    }
    val onLoginClicked: () -> Unit = {
        navController.navigate(AuthScreens.LoginScreen.route){
            popUpTo(AuthScreens.RegisterScreen.route){
                inclusive = true
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
                    focusedTextColor = if (viewModel.nameValid.value) Color.Black else errorColor,
                    unfocusedTextColor = if (viewModel.nameValid.value) Color.Black else errorColor
                )
            )
            errorFeild(text = "Error Fields", modifier = Modifier.padding(top = 470.dp))
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            errorFeild(text = "Error Fields", modifier = Modifier.padding(top = 570.dp))

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
                    focusedTextColor = if (viewModel.tempAddressValid.value) Color.Black else errorColor,
                    unfocusedTextColor = if (viewModel.tempAddressValid.value) Color.Black else errorColor
                ),
            )
            errorFeild(text = "Error Fields", modifier = Modifier.padding(top = 670.dp))

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
                    focusedTextColor = if (viewModel.permAddressValid.value) Color.Black else errorColor,
                    unfocusedTextColor = if (viewModel.permAddressValid.value) Color.Black else errorColor
                ),
            )
            errorFeild(text = "Error Fields", modifier = Modifier.padding(top = 770.dp))

            val options = listOf("Aadhar", "Voter ID", "Driving License","Ration Number" , "Passport", "PAN Card")
            val options1 = listOf("आधार", "मतदाता पहचान पत्र", "ड्राइविंग लाइसेंस","राशन संख्या", "पासपोर्ट", "पैन कार्ड")
            var selectedIndex by remember { mutableStateOf(0) }
            Box(
                modifier=Modifier.fillMaxWidth()
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
                        text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") options[selectedIndex] else options1[selectedIndex] +" " + "नंबर",
                        fontFamily = customFontFamily
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.identificationNumberValid.value) primaryVariantColor1 else errorColor,
                    focusedLabelColor = if (viewModel.identificationNumberValid.value) primaryVariantColor1 else errorColor,
                    cursorColor = if (viewModel.identificationNumberValid.value) primaryVariantColor1 else errorColor,
                    unfocusedBorderColor = if (viewModel.identificationNumberValid.value) gray else errorColor,
                    unfocusedLabelColor = if (viewModel.identificationNumberValid.value) gray else errorColor,
                    focusedTextColor = if (viewModel.identificationNumberValid.value) Color.Black else errorColor,
                    unfocusedTextColor = if (viewModel.identificationNumberValid.value) Color.Black else errorColor
                ),
            )
            errorFeild(text = "Error Fields", modifier = Modifier.padding(top = 970.dp))
            var checked by remember { mutableStateOf(true) }
            SwitchWithIconExample(modifier = Modifier.padding(top = 1020.dp, start=30.dp), checked, onCheckedChange = { checked = it })
            Text(
                text= if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English")
                    if(checked)"Borrower"
                    else "Lender"
                else
                if(checked)"उधार लेने वाला"
                else "उधार देने वाला",
                modifier = Modifier.padding(top = 1030.dp, start=100.dp),
                fontFamily = customFontFamily,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            SignupButton(
                text = if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "SignUp" else "साइन अप",
                onClick = { SignupClicked() },
                modifier = Modifier
                    .padding(top = 1110.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                )
            Text(
                text=if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Already have an account? Click Here" else "पहले से ही खाता है? यहाँ क्लिक करें",
                modifier = Modifier
                    .padding(top = 1210.dp, start = 16.dp, end = 16.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .clickable { onLoginClicked() },
//                    .clickableWithoutRipple(onLoginClicked),
                textAlign = TextAlign.Center,
                fontFamily = customFontFamily

            )
        }
    }
}
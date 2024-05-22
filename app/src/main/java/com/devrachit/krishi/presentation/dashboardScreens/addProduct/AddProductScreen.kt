package com.devrachit.krishi.presentation.dashboardScreens.addProduct

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devrachit.krishi.common.constants.customFontFamily
import com.devrachit.krishi.common.util.isLongAndLettersOnly
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.SignupButton
import com.devrachit.krishi.presentation.authScreens.signupScreen.components.errorFeild
import com.devrachit.krishi.presentation.dashboardScreens.addProduct.components.PredefCard
import com.devrachit.krishi.presentation.dashboardScreens.addProduct.components.SignupButton2
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading
import com.devrachit.krishi.ui.theme.errorColor
import com.devrachit.krishi.ui.theme.gray
import com.devrachit.krishi.ui.theme.primaryVariantColor1

@Composable
fun AddProductScreen(navController: NavController) {
    val viewModel: AddProductViewModel = hiltViewModel()
    val imageUrl by viewModel.imageUrl.collectAsState()
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val numberState = remember { mutableStateOf(TextFieldValue()) }
    val quantityState = remember { mutableStateOf(TextFieldValue()) }
    val daysState= remember { mutableStateOf(TextFieldValue()) }
    val isButtonEnabled = viewModel.imageUrl.collectAsStateWithLifecycle().value != null
    val dataFetch= viewModel.dataFetch.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val predef= viewModel.preDef.collectAsStateWithLifecycle().value
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.uploadProductImage(it) }
        }
    val onAddProductClicked: () -> Unit = {
        if(nameState.value.text.isLongAndLettersOnly() && numberState.value.text.isNotEmpty())
        {
            viewModel.nameValid.value = true
            viewModel.numberValid.value = true
            viewModel.addItem(name=nameState.value.text, price=numberState.value.text, imageUrl =  imageUrl.toString(), quantity = quantityState.value.text, days = daysState.value.text)
        }
        else{
            viewModel.nameValid.value = false
            viewModel.numberValid.value = false
        }
    }


    if(dataFetch)
    {
        viewModel.setDataFetch(false)
        Toast.makeText(context,
            if(viewModel.sharedViewModel.getLanguage()=="English")"Product Added Successfully" else "उत्पाद सफलतापूर्वक जोड़ा गया",
            Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(true) {
        viewModel.fetchPreDef()
    }
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    )
    {
        item{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp)
            )
            {
                Box(modifier = Modifier
                    .size(200.dp)
                    .clickable { launcher.launch("image/*") }
                    .clip(CircleShape)
                )
                {
                    val painter = if (imageUrl != null) {
                        rememberAsyncImagePainter(imageUrl)
                    } else {
                        rememberAsyncImagePainter("https://via.placeholder.com/200")
                    }
                    Image(painter = painter, contentDescription = null, modifier = Modifier.size(200.dp))
                }
                Text(
                    text = if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Add Product Image" else "उत्पाद छवि जोड़ें",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 20.dp)
                )

                OutlinedTextField(
                    value = nameState.value,
                    onValueChange = { nameState.value = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Product Name" else "उत्पाद का नाम",
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
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = numberState.value,
                    onValueChange = { numberState.value = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Price Per Day" else "प्रतिदिन मूल्य",
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
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = quantityState.value,
                    onValueChange = { quantityState.value = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Quantity" else "मात्रा",
                            fontFamily = customFontFamily
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (viewModel.quantityValid.value) primaryVariantColor1 else errorColor,
                        focusedLabelColor = if (viewModel.quantityValid.value) primaryVariantColor1 else errorColor,
                        cursorColor = if (viewModel.quantityValid.value) primaryVariantColor1 else errorColor,
                        unfocusedBorderColor = if (viewModel.quantityValid.value) gray else errorColor,
                        unfocusedLabelColor = if (viewModel.quantityValid.value) gray else errorColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                )
                errorFeild(
                    text = if (viewModel.quantityValid.value) "" else "Recheck",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = daysState.value,
                    onValueChange = { daysState.value = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Days" else "दिन",
                            fontFamily = customFontFamily
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (viewModel.quantityValid.value) primaryVariantColor1 else errorColor,
                        focusedLabelColor = if (viewModel.quantityValid.value) primaryVariantColor1 else errorColor,
                        cursorColor = if (viewModel.quantityValid.value) primaryVariantColor1 else errorColor,
                        unfocusedBorderColor = if (viewModel.quantityValid.value) gray else errorColor,
                        unfocusedLabelColor = if (viewModel.quantityValid.value) gray else errorColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                )
                errorFeild(
                    text = if (viewModel.quantityValid.value) "" else "Recheck",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.Start)
                )
                SignupButton2(
                    text = if (viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Add Product" else "उत्पाद जोड़ें",
                    onClick = { onAddProductClicked.invoke()},
                    modifier = Modifier
                        .padding(top = 70.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    enabled = isButtonEnabled
                )
            }

            if (loading.value) {
                LoadingDialog(isShowingDialog = true)
            }
            else{
                LoadingDialog(isShowingDialog =false)
            }
            Heading(
                text =if(viewModel.sharedViewModel.language.collectAsStateWithLifecycle().value == "English") "Predefined Products" else "पूर्वनिर्धारित उत्पाद",
                modifier =Modifier.padding(top=30.dp) )
        }
        items(predef.size){index->
            val predefinedProductName = predef.keys.toList()[index]
            val predefinedProductImageUrl = predef.values.toList()[index].toString()

//            PredefCard(imageUrl = predef.values.toList()[it].toString(), name = predef.keys.toList()[it])
            PredefCard(
                imageUrl = predefinedProductImageUrl,
                name = predefinedProductName,
                modifier = Modifier.clickable {
                    nameState.value = TextFieldValue(predefinedProductName)
                    viewModel.setImageUrl(predefinedProductImageUrl)
                }
            )
        }

    }

}
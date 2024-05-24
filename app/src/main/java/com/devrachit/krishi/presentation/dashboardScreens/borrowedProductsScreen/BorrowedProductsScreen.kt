package com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.krishi.domain.models.itemModel2
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen.components.ProductCard8
import com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen.components.ProductCard9
import com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen.components.ReviewDialog
import com.devrachit.krishi.presentation.dashboardScreens.madeRequestScreen.components.ProductCard4
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components.DialogCon

@Composable
fun BorrowedProductsScreen(navController: NavController) {
    val viewModel : BorrowedProductsViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val scope = rememberCoroutineScope()
    val context= LocalContext.current
    val user: userModel = viewModel.sharedViewModel.getUser()
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val dataFetch = viewModel.dataFetch.collectAsStateWithLifecycle()
    var items = viewModel.sharedViewModel.selfUploads2.collectAsStateWithLifecycle().value
    val showDialogBox = remember { mutableStateOf(false) }
    var ownerDetails =remember{ mutableStateOf(userModel("","","","","","",true)) }
    val paymentSuccess = viewModel.paymentState.collectAsStateWithLifecycle().value
    val itemSelected = remember { mutableStateOf(itemModel2("","","","","","","","","")) }
    val showReviewDialog = remember { mutableStateOf(false) }
    LaunchedEffect(paymentSuccess) {
        when (paymentSuccess) {
            is PaymentState.Success -> {
                viewModel.updatePaymentStatus(paymentSuccess.paymentId.toString(),itemSelected.value)
            }
            is PaymentState.Failure -> {
                Log.d("Payment", "Payment failed with error code ${paymentSuccess.errorCode} and message ${paymentSuccess.errorMessage}")
            }
            else -> {
                Log.d("Payment", "Payment failed with error code ${paymentSuccess} and message ${paymentSuccess}")
            }
        }

    }
    val onItemClicked: (itemModel2) -> Unit = {
        println("Borrowed Item Clicked ${it.paid}")
        viewModel.fetchOwnerDetails(it){
            println("Borrower Details fetched ${it}")
            ownerDetails.value=it
            showDialogBox.value=true
        }
    }
    val onReviewClick: (itemModel2) -> Unit = {
        itemSelected.value=it
        showReviewDialog.value=true
    }
    val onPayClick : (itemModel2) -> Unit = {
        itemSelected.value=it
        viewModel.payForProduct(it,context)
    }
    if(showDialogBox.value){
        DialogCon(
            isShowingDialog = showDialogBox.value,
            userDetails =  ownerDetails.value,
            onDismissRequest = {
                showDialogBox.value=false
            })
    }
    if(showReviewDialog.value)
    {
        ReviewDialog(
            isShowingDialog = showReviewDialog.value,
            onDismissRequest = {
                showReviewDialog.value=false
            },
            onSubmitReview = {review,rating->
            }
        )

    }
    Scaffold(
        containerColor = Color.White,
    ) {
        Log.d("MainScreen", it.toString())

        LazyColumn(
            modifier = Modifier
                .padding(top = 0.dp)
                .background(Color.White)
        ) {
            item{
                Heading(
                    text=if(viewModel.sharedViewModel.getLanguage()=="English") "Borrowed Products" else "उधारी उत्पाद",
                    Modifier.padding(top = 70.dp, start = 20.dp))
            }
            items(items.size) {
                if(items[it].paid==true)
                    ProductCard9(itemModel = items[it], {onReviewClick.invoke(it)},{onItemClicked.invoke(it)})
                else
                    ProductCard8(itemModel = items[it], {onPayClick.invoke(it)},{onItemClicked.invoke(it) })

            }

        }
    }
    if (loading.value) {
        LoadingDialog(isShowingDialog = true)
    }
    else{
        LoadingDialog(isShowingDialog =false)
    }
}
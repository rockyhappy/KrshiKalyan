package com.devrachit.krishi.presentation.dashboardScreens.myRequestsScreen

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.dashboardScreens.madeRequestScreen.components.ProductCard4
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components.DialogCon
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components.DialogContent
import com.devrachit.krishi.presentation.dashboardScreens.myRequestsScreen.component.ProductCard5

@Composable
fun MyRequestScreen(navController: NavController) {
    val viewModel: MyRequestScreenViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val user: userModel = viewModel.sharedViewModel.getUser()
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val dataFetch = viewModel.dataFetch.collectAsStateWithLifecycle()
    var items = viewModel.sharedViewModel.borrowerRequests.collectAsStateWithLifecycle().value
    val showDialogBox = remember { mutableStateOf(false) }
    val showDialogBorrowerDetails = remember { mutableStateOf(false) }
    var borrowerDetails =remember{ mutableStateOf(userModel("","","","","","",true)) }
    if(showDialogBox.value){
        DialogCon(
            isShowingDialog = showDialogBox.value,
            userDetails =  borrowerDetails.value,
            onDismissRequest = {
                showDialogBox.value=false
            })
    }
    val onItemClicked: (itemModel) -> Unit = {
        viewModel.fetchBorrowerDetails(it){
            println("Borrower Details fetched ${it.tempAddress}")
            borrowerDetails.value=it
            showDialogBox.value=true
        }
    }
    val onApproveClick : (itemModel) -> Unit = {
        viewModel.approveRequest(it)
    }
    LaunchedEffect(key1=true) {
        viewModel.getMyRequests()
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
                    text= if(viewModel.sharedViewModel.getLanguage()=="English") "My Requests" else "मेरे अनुरोध",
                    Modifier.padding(top = 70.dp, start = 20.dp))
            }
            items(items.size) {
                ProductCard5(itemModel = items[it], onItemClick= {onItemClicked(it)}, onApproveClick = {onApproveClick(it)})
                println("My borrower Screen ${items[it].name}")
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
package com.devrachit.krishi.presentation.dashboardScreens.myBorrowers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.domain.models.itemModel2
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.navigation.dashboard.DashScreens
import com.devrachit.krishi.presentation.dashboardScreens.madeRequestScreen.components.ProductCard4
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.ProductCard
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components.DialogCon
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components.DialogContent
import com.devrachit.krishi.presentation.dashboardScreens.myBorrowers.components.ProductCard2
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import kotlinx.coroutines.launch


//this is the screen for the Lender side and the borrowed items are shown on this screen

@Composable
fun MyBorrowersScreen(navController: NavController) {
    val viewModel: MyBorrowersViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val user: userModel = viewModel.sharedViewModel.getUser()
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val dataFetch = viewModel.dataFetch.collectAsStateWithLifecycle()
    var items = viewModel.sharedViewModel.selfUploads2.collectAsStateWithLifecycle().value
    val showDialogBox = remember { mutableStateOf(false) }
    val onDeleteClick: (itemModel: itemModel2) -> Unit = {
        var item = it
        viewModel.deleteItem(item)
        viewModel.deleteItem(item)
    }
    val onItemClick: (itemModel:itemModel2) -> Unit = {
        viewModel.fetchBorrowerDetails(it){
            println("Borrower Details fetched ${it.name}")
            showDialogBox.value=true
            viewModel.sharedViewModel.setBorrowerDetails(it)
        }
    }
    if(showDialogBox.value){
        DialogCon(
            isShowingDialog = showDialogBox.value,
            userDetails =  viewModel.sharedViewModel.getBorrowerDetails(),
            onDismissRequest = {
            showDialogBox.value=false
        })
    }
    if (dataFetch.value) {
        items = viewModel.sharedViewModel.getSelfUploads2()
        viewModel.setDataFetch(false)
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
                    text= if(viewModel.sharedViewModel.getLanguage()=="English")"My Borrowers" else "मेरे उधारी",
                    Modifier.padding(top = 70.dp, start = 20.dp))
            }
            items(items.size) {
                ProductCard4(itemModel = items[it],  { onItemClick.invoke(it) })
                println("My borrower Screen ${items[it].name}")
            }

        }
    }
}
package com.devrachit.krishi.presentation.dashboardScreens.mainScreenBorrower

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.domain.models.itemModel2
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.navigation.auth.AuthScreens
import com.devrachit.krishi.navigation.dashboard.DashScreens
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.MainScreenViewModel
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.DrawerItem
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.NavigationDrawerHeader
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.ProductCard
import com.devrachit.krishi.presentation.dashboardScreens.mainScreenBorrower.components.ProductCard3
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainScreenBorrower(navController: NavController)
{
    val viewModel: MainScreenBorrowerViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val user : userModel = viewModel.sharedViewModel.getUser()
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val dataFetch = viewModel.dataFetch.collectAsStateWithLifecycle()
    val items = viewModel.sharedViewModel.selfUploads.collectAsStateWithLifecycle().value
    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle().value
    val context= navController.context
    val onLogOutClick : () -> Unit = {
        viewModel.logout()
       val intent = Intent(context, Auth::class.java)
        context.startActivity(intent)
        (context as Auth).finish()
    }
    val onContactUsClick : () -> Unit = {
       navController.navigate(DashScreens.ContactUsScreen.route) {
           launchSingleTop = true
       }
        scope.launch { drawerState.close()}

    }
    val onRequestBooking : (itemModel: itemModel2) -> Unit = {
        viewModel.addItemToBorrow(it)
    }
    val onHomeClick: ()->Unit ={
        navController.navigate(DashScreens.MainScreenBorrower.route){
            launchSingleTop = true
        }
        scope.launch { drawerState.close() }
    }
    val onMyRequestClick:()->Unit={
        navController.navigate(DashScreens.MadeRequestScreen.route){
            launchSingleTop = true
        }
        scope.launch { drawerState.close() }
    }
    val onMyBorrowedProductsClick:()->Unit={
        navController.navigate(DashScreens.BorrowedProductScreen.route){
            launchSingleTop = true
        }
        scope.launch { drawerState.close() }
    }
    ModalNavigationDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                NavigationDrawerHeader()
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Home" else "घर",
                    onClick = { onHomeClick.invoke() }, Icon = Icons.Filled.AccountCircle )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "My Requests" else "मेरे अनुरोध",
                    onClick = { onMyRequestClick.invoke()}, Icon = Icons.Filled.Info )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "My Borrowed Item" else "मेरे उधारी उत्पाद",
                    onClick = { onMyBorrowedProductsClick.invoke()}, Icon = Icons.Filled.CheckCircle )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Contact Us" else "संपर्क करें",
                    onClick = { onContactUsClick.invoke()}, Icon = Icons.Filled.Call )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Log Out" else "लॉग आउट",
                    onClick = { onLogOutClick.invoke()}, Icon = Icons.Filled.ExitToApp )
            }
        },
        drawerState = drawerState
    )
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Hello "+user.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primaryVariantColor1
                    )
                )
            },
            containerColor = Color.White,
        ) {
            Log.d("MainScreen Borrower", it.toString())
            LazyColumn(
                modifier= Modifier
                    .padding(top = 120.dp)
                    .background(Color.White)
            ) {

                if(!loading.value && dataFetch.value)
                {
                    item{
                        Heading(
                            text=if(viewModel.sharedViewModel.getLanguage()=="English") "My available borrows" else "मेरे उपलब्ध उधार",
                            Modifier.padding(top=0.dp, start=20.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { newValue -> viewModel.setSearchQuery(newValue) },
                            placeholder = { Text(
                                if(viewModel.sharedViewModel.getLanguage()=="English") "Search" else "खोजें",
                            ) },
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                                .background(Color.White),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )
                    }
                    items(items.size) {
                        ProductCard3(itemModel = items[it],  onRequestBooking = {onRequestBooking.invoke(it)})
                        println("Main Screen Borrower ${items[it].name}")
                    }
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
}
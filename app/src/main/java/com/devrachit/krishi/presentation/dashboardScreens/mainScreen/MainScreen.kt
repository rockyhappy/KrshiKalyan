package com.devrachit.krishi.presentation.dashboardScreens.mainScreen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import com.devrachit.krishi.presentation.authScreens.Auth
import com.devrachit.krishi.presentation.authScreens.loginScreen.components.LoadingDialog
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.DrawerItem
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.Heading
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.LogoutConfirmationDialog
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.NavigationDrawerHeader
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.ProductCard
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainScreenLender(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val user : userModel= viewModel.sharedViewModel.getUser()
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    val dataFetch = viewModel.dataFetch.collectAsStateWithLifecycle()
    val items = viewModel.sharedViewModel.selfUploads.collectAsStateWithLifecycle().value
    val context= navController.context
    val showLogoutDialog = remember { mutableStateOf(false) }
    val onConfirmLogout : () -> Unit = {
        viewModel.logout()
        val intent = Intent(context, Auth::class.java)
        context.startActivity(intent)
        (context as Auth).finish()
    }
    val onlogOutClick: () -> Unit = {
        showLogoutDialog.value = true
    }
    val onContactUsClick : () -> Unit = {
        navController.navigate(DashScreens.ContactUsScreen.route) {
            launchSingleTop = true
        }
        scope.launch { drawerState.close()}
    }
    val onMyBorrowersClick : () -> Unit = {
        navController.navigate(DashScreens.MyBorrowersScreen.route){
            launchSingleTop = true
        }
        scope.launch { drawerState.close()}
    }
    val onMyLendsClick : () -> Unit = {
        scope.launch { drawerState.close()}
    }
    val onDeleteClick : (itemModel: itemModel2) -> Unit = {
        var item = it
        viewModel.deleteItem(item)

    }
    val onAddProductClick : () -> Unit = {
        navController.navigate(DashScreens.AddProductScreen.route){
            launchSingleTop = true
        }
        scope.launch { drawerState.close()}
    }
    val onMyRequestClick:() -> Unit ={
        navController.navigate(DashScreens.MyRequestScreen.route){
            launchSingleTop = true
        }
        scope.launch { drawerState.close() }
    }
    LaunchedEffect(key1=true)
    {
        viewModel.getSelfUploads()
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
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Home" else "होम",
                    onClick = {onMyLendsClick.invoke() }, Icon = Icons.Filled.AccountCircle  )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "My Requests" else "मेरे अनुरोध",
                    onClick = {onMyRequestClick.invoke() }, Icon = Icons.Filled.Info  )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "My Borrowers" else "मेरे उधारदाता",
                    onClick = {onMyBorrowersClick.invoke() }, Icon = Icons.Filled.CheckCircle  )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Contact Us" else "संपर्क करें",
                    onClick = { onContactUsClick.invoke()}, Icon = Icons.Filled.Call )
                DrawerItem(
                    text = if(viewModel.sharedViewModel.getLanguage()=="English") "Log Out" else "लॉग आउट",
                    onClick = { onlogOutClick.invoke()}, Icon = Icons.Filled.ExitToApp )
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
            floatingActionButton = {
                FloatingActionButton(onClick = { onAddProductClick()}, containerColor = primaryVariantColor1) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Call", tint = Color.White)
                }
            },
            ) {
            Log.d("MainScreen", it.toString())
//            Heading("My available Lends", Modifier.padding(top=100.dp, start=20.dp))
            LazyColumn(
                modifier= Modifier
                    .padding(top = 100.dp)
                    .background(Color.White)
            ) {

                if(!loading.value && dataFetch.value)
                {
                    item{
                        Heading(
                            text= if(viewModel.sharedViewModel.getLanguage()=="English") "My available Lends" else "मेरे उपलब्ध उधार",
                            Modifier.padding(top = 0.dp, start = 20.dp))
                    }
                    items(items.size) {
                        ProductCard(itemModel = items[it], onDeleteClick = {onDeleteClick.invoke(it)})
                        println("Main Screen ${items[it].name}")
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
        if (showLogoutDialog.value) {
            LogoutConfirmationDialog(
                onConfirm = {
                    showLogoutDialog.value = false
                    onConfirmLogout()
                },
                onDismiss = {
                    showLogoutDialog.value = false
                }
            )
        }
    }
}
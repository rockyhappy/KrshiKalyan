package com.devrachit.krishi.presentation.dashboardScreens.mainScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devrachit.krishi.domain.models.NavigationItem
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.DrawerItem
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.NavigationDrawerHeader
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainScreenLender(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val user : userModel= viewModel.sharedViewModel.getUser()
    val onlogOutClick : () -> Unit = {

    }
    val onContactUsClick : () -> Unit = {

    }
    val onMyBorrowersClick : () -> Unit = {

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
                DrawerItem(text = "My Borrowers", onClick = {onMyBorrowersClick.invoke() }, Icon = Icons.Filled.Info )
                DrawerItem(text = "Contact Us", onClick = { onContactUsClick.invoke()}, Icon = Icons.Filled.Call )
                DrawerItem(text = "Log Out", onClick = { onlogOutClick.invoke()}, Icon = Icons.Filled.ExitToApp )
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
            containerColor = Color.White
            ) {
            Log.d("MainScreen", it.toString())
        }
    }
}
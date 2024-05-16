package com.devrachit.krishi.presentation.dashboardScreens.mainScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devrachit.krishi.presentation.dashboardScreens.mainScreen.components.NavigationDrawerHeader
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainScreenLender(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val drawerState=rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent={
            ModalDrawerSheet {
                NavigationDrawerHeader()
                Column (modifier=Modifier.background(Color.White).width(260.dp).fillMaxHeight()){
                    Text(text = "Home", fontSize = 20.sp, modifier = Modifier.padding(16.dp), color = Color.Black)
                    Text(text = "Profile", fontSize = 20.sp, modifier = Modifier.padding(16.dp), color = Color.Black)
                    Text(text = "Settings", fontSize = 20.sp, modifier = Modifier.padding(16.dp), color = Color.Black)
                    Text(text = "Logout", fontSize = 20.sp, modifier = Modifier.padding(16.dp), color = Color.Black)
                }
                }

        },
        drawerState=drawerState
    )
    {
        Scaffold(
            topBar ={
                TopAppBar(
                    title ={ Text(text = "Krishi", color = Color.Black, fontSize = 20.sp) },
                    navigationIcon={IconButton(onClick={scope.launch{drawerState.open()}}){
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    } }
                )
            },


        ) {
            Log.d("MainScreen",it.toString())
        }
    }
}
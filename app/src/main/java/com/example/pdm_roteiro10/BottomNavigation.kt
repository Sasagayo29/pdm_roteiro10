package com.example.pdm_roteiro10

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = remember { AppDatabase.getDatabase(context) }

    val vehiclesViewModel: VehiclesViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VehiclesViewModel(database.vehicleDao()) as T
            }
        }
    )

    val groupsViewModel: GroupsViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GroupsViewModel(database.groupDao()) as T
            }
        }
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute == Screen.VehiclesList.route || currentRoute == Screen.GroupsList.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Veículos") },
                        label = { Text("Veículos") },
                        selected = currentRoute == Screen.VehiclesList.route,
                        onClick = {
                            navController.navigate(Screen.VehiclesList.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Grupos") },
                        label = { Text("Grupos") },
                        selected = currentRoute == Screen.GroupsList.route,
                        onClick = {
                            navController.navigate(Screen.GroupsList.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.VehiclesList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.VehiclesList.route) {
                VehiclesList(vehiclesViewModel) {
                    navController.navigate(Screen.VehicleForm.route)
                }
            }
            composable(Screen.VehicleForm.route) {
                VehicleForm(vehiclesViewModel) {
                    navController.popBackStack()
                }
            }
            composable(Screen.GroupsList.route) {
                GroupList(groupsViewModel) {
                    navController.navigate(Screen.GroupForm.route)
                }
            }
            composable(Screen.GroupForm.route) {
                GroupForm(groupsViewModel) {
                    navController.popBackStack()
                }
            }
        }
    }
}
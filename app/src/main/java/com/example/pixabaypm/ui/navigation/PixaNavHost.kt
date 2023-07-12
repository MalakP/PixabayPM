package com.example.pixabaypm.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.pixabaypm.ui.SharedViewModel
import com.example.pixabaypm.ui.compose.DetailsScreen
import com.example.pixabaypm.ui.compose.SearchScreen

@Composable
fun PixaNavHost(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = App.route,
        modifier = Modifier.fillMaxSize()
    ) {
        navigation(
            startDestination = Search.route,
            route = App.route
        ) {
            composable(route = Search.route) { navBackStackEntry ->
                val viewModel =
                    navBackStackEntry.sharedViewModel<SharedViewModel>(navController = navController)
                SearchScreen(
                    viewModel,
                    onRowClick = { navController.navigateToDetails(it) })
            }
            composable(route = Details.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                        defaultValue = 0
                    }
                )
            ) { backStackEntry ->
                val viewModel =
                    backStackEntry.sharedViewModel<SharedViewModel>(navController = navController)
                DetailsScreen(
                    viewModel,
                    backStackEntry.arguments?.getLong("id") ?: 0L,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

private fun NavHostController.navigateToDetails(id: Long) {
    this.navigate(Details.route + "/$id")
}
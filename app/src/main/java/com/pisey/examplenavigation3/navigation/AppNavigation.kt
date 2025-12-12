package com.pisey.examplenavigation3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pisey.examplenavigation3.ui.screen.*

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Routes.UserList.route) {
            UserListScreen(navController = navController)
        }
        
        composable(
            route = Routes.UserDetail.route,
            arguments = listOf(
                navArgument(NavArguments.USER_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            UserDetailScreen(navController = navController)
        }
        
        composable(Routes.Settings.route) {
            SettingsScreen(navController = navController)
        }
        
        composable(
            route = Routes.Profile.route,
            arguments = listOf(
                navArgument(NavArguments.USER_ID) {
                    type = NavType.StringType
                },
                navArgument(NavArguments.USER_NAME) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(NavArguments.USER_ID) ?: ""
            val userName = backStackEntry.arguments?.getString(NavArguments.USER_NAME)
            ProfileScreen(
                navController = navController,
                userId = userId,
                userName = userName
            )
        }
    }
}
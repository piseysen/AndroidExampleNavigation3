package com.pisey.examplenavigation3.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.pisey.examplenavigation3.navigation.AppNavigation
import com.pisey.examplenavigation3.navigation.BottomNavItem
import com.pisey.examplenavigation3.navigation.Home
import com.pisey.examplenavigation3.navigation.Login
import com.pisey.examplenavigation3.navigation.Settings
import com.pisey.examplenavigation3.navigation.UserList

@Composable
fun MainScreen() {
    val backStack = rememberNavBackStack(Login)
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(backStack = backStack)
        }
    ) { paddingValues ->
        AppNavigation(
            backStack = backStack,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun BottomNavigationBar(backStack: NavBackStack<NavKey>) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Users,
        BottomNavItem.Settings
    )
    
    // Get current top entry from backStack
    val currentKey = if (backStack.isNotEmpty()) backStack.last() else null
    
    val shouldShowBottomBar = when (currentKey) {
        is Home, is UserList, is Settings -> true
        else -> false
    }
    
    if (shouldShowBottomBar) {
        NavigationBar {
            items.forEach { item ->
                val selected = when (item.route) {
                    "home" -> currentKey is Home
                    "user_list" -> currentKey is UserList
                    "settings" -> currentKey is Settings
                    else -> false
                }
                
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        when (item.route) {
                            "home" -> {
                                backStack.clear()
                                backStack.add(Home)
                            }
                            "user_list" -> {
                                backStack.clear()
                                backStack.add(UserList)
                            }
                            "settings" -> {
                                backStack.clear()
                                backStack.add(Settings)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
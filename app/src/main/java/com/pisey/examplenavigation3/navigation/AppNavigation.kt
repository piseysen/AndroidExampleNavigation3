package com.pisey.examplenavigation3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.pisey.examplenavigation3.ui.screen.*

@Composable
fun AppNavigation(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Login> {
                LoginScreen(backStack = backStack)
            }
            
            entry<Home> {
                HomeScreen(backStack = backStack)
            }
            
            entry<UserList> {
                UserListScreen(backStack = backStack)
            }
            
            entry<UserDetail> { userDetail ->
                UserDetailScreen(
                    backStack = backStack,
                    userId = userDetail.userId
                )
            }
            
            entry<Settings> {
                SettingsScreen(backStack = backStack)
            }
            
            entry<Profile> { profile ->
                ProfileScreen(
                    backStack = backStack,
                    userId = profile.userId,
                    userName = profile.name
                )
            }
        }
    )
}
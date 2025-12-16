package com.pisey.examplenavigation3.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Login : NavKey

@Serializable
data object Home : NavKey

@Serializable
data object UserList : NavKey

@Serializable
data class UserDetail(val userId: String) : NavKey

@Serializable
data object Settings : NavKey

@Serializable
data class Profile(val userId: String, val name: String? = null) : NavKey

// Legacy routes for fallback compatibility
sealed class Routes(val route: String) {
    data object Login : Routes("login")
    data object Home : Routes("home")
    data object UserList : Routes("user_list")
    data object UserDetail : Routes("user_detail/{userId}") {
        fun createRoute(userId: String) = "user_detail/$userId"
    }
    data object Settings : Routes("settings")
    data object Profile : Routes("profile/{userId}?name={name}") {
        fun createRoute(userId: String, name: String? = null): String {
            return if (name != null) {
                "profile/$userId?name=$name"
            } else {
                "profile/$userId"
            }
        }
    }
}
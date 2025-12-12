package com.pisey.examplenavigation3.navigation

sealed class Routes(val route: String) {
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

object NavArguments {
    const val USER_ID = "userId"
    const val USER_NAME = "name"
}
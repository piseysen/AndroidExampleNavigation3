package com.pisey.examplenavigation3.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val isActive: Boolean = true
)
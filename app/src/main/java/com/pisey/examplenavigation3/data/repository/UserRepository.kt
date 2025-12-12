package com.pisey.examplenavigation3.data.repository

import com.pisey.examplenavigation3.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: String): User?
    fun getUsersFlow(): Flow<List<User>>
    suspend fun updateUser(user: User)
}

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    
    private val mockUsers = mutableListOf(
        User("1", "John Doe", "john@example.com", null, "Android Developer"),
        User("2", "Jane Smith", "jane@example.com", null, "iOS Developer"),
        User("3", "Bob Johnson", "bob@example.com", null, "Full Stack Developer"),
        User("4", "Alice Brown", "alice@example.com", null, "UI/UX Designer"),
        User("5", "Charlie Wilson", "charlie@example.com", null, "Project Manager")
    )
    
    override suspend fun getUsers(): List<User> {
        delay(500) // Simulate network delay
        return mockUsers.toList()
    }
    
    override suspend fun getUserById(userId: String): User? {
        delay(300) // Simulate network delay
        return mockUsers.find { it.id == userId }
    }
    
    override fun getUsersFlow(): Flow<List<User>> = flow {
        while (true) {
            emit(mockUsers.toList())
            delay(5000) // Emit every 5 seconds
        }
    }
    
    override suspend fun updateUser(user: User) {
        delay(300) // Simulate network delay
        val index = mockUsers.indexOfFirst { it.id == user.id }
        if (index != -1) {
            mockUsers[index] = user
        }
    }
}
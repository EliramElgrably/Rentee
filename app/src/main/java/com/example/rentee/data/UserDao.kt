package com.example.rentee.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY user_id")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun getUserById(userId: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM users WHERE user_id = :userId")
    suspend fun deleteUserById(userId: String)

    @Delete
    suspend fun deleteUser(user: User)

    @Transaction
    @Query("SELECT * FROM users")
    fun getUserWithRentals(): List<UserWithRentals>
}
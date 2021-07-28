package com.example.rentee.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "photo_url") val photoUrl: String?,
)

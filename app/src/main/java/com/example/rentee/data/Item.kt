package com.example.rentee.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "item_id") val itemId: Int = 0,
    @ColumnInfo(name = "rental_related_id") val userCreatorId: Int?,
    @ColumnInfo(name = "name") val name: String,
)

package com.example.rentee.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "item_id") var itemId: String = " ",
    @ColumnInfo(name = "rental_related_id") val userCreatorId: Int?,
    @ColumnInfo(name = "name") val name: String?,
) {
    constructor() : this(" ",null, null)
}

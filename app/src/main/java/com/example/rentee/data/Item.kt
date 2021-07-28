package com.example.rentee.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "item_id") var itemId: String = " ",
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image_url") var url: String?,
    @ServerTimestamp
    @ColumnInfo(name = "server_update_time") var serverUpdateTime: Date?,
) {
    constructor() : this(" ",null, null, null, null)
    constructor(item: Item) : this(item.itemId, item.userId, item.name, item.url, item.serverUpdateTime)
}

package com.example.rentee.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY server_update_time")
    fun getItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE item_id = :itemId")
    fun getItemById(itemId: Int): Flow<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()

    @Query("DELETE FROM items WHERE item_id = :itemId")
    suspend fun deleteItemById(itemId: Int)

    @Delete
    suspend fun deleteItem(item: Item)

}
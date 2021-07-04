package com.example.rentee.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao){
    val allItems: Flow<List<Item>> = itemDao.getItems()

    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }
}
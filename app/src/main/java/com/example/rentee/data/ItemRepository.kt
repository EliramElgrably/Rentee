package com.example.rentee.data

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val modelFirebase: ModelFirebase
) {
    val allItems: Flow<List<Item>> = itemDao.getItems()

    suspend fun refreshItemsList() {
        try {
            val networkListItems: ArrayList<Item> = modelFirebase.getAllItemsSuspendAwait()

            if (networkListItems.isNotEmpty()) {
                itemDao.insertAll(networkListItems)
            }
        } catch (error: Throwable) {
            throw ItemRefreshError("Unable to refresh title", error)
        }
    }

    //suspend fun uploadItemImage()

    // Todo:remove android's Bitmap
    suspend fun insert(bitmap: Bitmap, item: Item) {
        modelFirebase.uploadNewItemSuspendAwait(item).let { itemDocId ->
            if (itemDocId != null) {
                modelFirebase.uploadImage(itemDocId, bitmap).let {itemImageUrl ->
                    if(itemImageUrl != null){
                        item.url = itemImageUrl
                        item.itemId = itemDocId

                        modelFirebase.updateItemSuspendAwait(item)
                    }
                    refreshItemsList()}
            }
        }

    }

    /**
     * Thrown when there was a error fetching new items
     *
     * @property message user ready error message
     * @property cause the original cause of this exception
     */
    class ItemRefreshError(message: String, cause: Throwable) : Throwable(message, cause)


    //TODO:check why it is not working
//    suspend fun refreshItemsListNotSuspended() {
//        // interact with *blocking* network and IO calls from a coroutine
//        withContext(Dispatchers.IO) {
//            val result = try {
//                // Make network request using a blocking call
//                getAllItems()
//            } catch (cause: Throwable) {
//                // If the network throws an exception, inform the caller
//                null
//            }
//
//            if (result != null && result.isNotEmpty()){
//                itemDao.insertAll(result)
//            }
//        }
//    }
}
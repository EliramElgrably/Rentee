package com.example.rentee.data

import android.util.Log
import com.example.rentee.utilities.FIREBASE_ITEM_COLLECTION
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelFirebase @Inject constructor() {
    //companion object {
    private val TAG = "ModelFirebase:"


    //////////////////////// suspended methods ////////////////////////////////////////////////////
    suspend fun uploadNewItemSuspendAwait(item: Item): Boolean {
        var retVal = true

        try {
            Firebase.firestore
                .collection(FIREBASE_ITEM_COLLECTION)
                .add(item)
                .await()
        } catch (e: Exception) {
            retVal = false
        }

        return retVal
    }

    suspend fun getAllItemsSuspendAwait(): ArrayList<Item> {
        val itemsList = ArrayList<Item>()

        try {
            val documents = Firebase.firestore
                .collection(FIREBASE_ITEM_COLLECTION)
                .get()
                .await()

            for (document in documents) {
                val item = document.toObject(Item::class.java)
                item.itemId = document.id
                itemsList.add(item)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error getting item documents: ", e)
        }

        return itemsList
    }

//        suspend fun uploadImage(): ArrayList<Item> {


    /////////// Not suspended methods - in this case we should make a IO dispatcher/////////////
    fun uploadNewItem(item: Item) {
        Firebase.firestore.collection(FIREBASE_ITEM_COLLECTION).document(item.itemId.toString())
            .set(item)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written with ID:")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }


    fun getAllItems(): ArrayList<Item> {
        var retVal: ArrayList<Item> = ArrayList()

        Firebase.firestore.collection(FIREBASE_ITEM_COLLECTION)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = document.toObject(Item::class.java)
                    retVal.add(item)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting item documents: ", exception)
            }

        return retVal
    }
    //}

}
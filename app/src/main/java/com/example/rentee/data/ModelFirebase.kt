package com.example.rentee.data

import android.graphics.Bitmap
import android.util.Log
import com.example.rentee.utilities.FIREBASE_ITEM_COLLECTION
import com.example.rentee.utilities.FIREBASE_USER_COLLECTION
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelFirebase @Inject constructor() {
    //companion object {
    private val TAG = "ModelFirebase:"


    //////////////////////// suspended methods ////////////////////////////////////////////////////
    suspend fun uploadNewItemSuspendAwait(item: Item): String? {
        var retVal: String? = null

        try {
            val documentRef = Firebase.firestore
                .collection(FIREBASE_ITEM_COLLECTION)
                .add(item)
                .await()
            retVal = documentRef.id
        } catch (e: Exception) {
            // TODO:exception
        }

        return retVal
    }

    // Todo: make gerneric methods for set and update
    suspend fun uploadNewUserSuspendAwait(user: User): Boolean{
        var retVal = false

        try {
            val documentRef = Firebase.firestore
                .collection(FIREBASE_USER_COLLECTION).document(user.userId)
                .set(user)
                .await()
            retVal = true
        } catch (e: Exception) {
            // TODO:exception
        }

        return retVal
    }

    suspend fun updateItemSuspendAwait(item: Item): Boolean {
        var retVal= true

        try {
            Firebase.firestore
                .collection(FIREBASE_ITEM_COLLECTION).document(item.itemId).set(item)
                .await()
        } catch (e: Exception) {
            // TODO:exception
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

    suspend fun uploadImage(itemId : String, bitmap: Bitmap): String? {
        // Get the data from an ImageView as bytes
//        imageView.isDrawingCacheEnabled = true
//        imageView.buildDrawingCache()
//        val bitmap = (imageView.drawable as BitmapDrawable).bitmap

        var retVal: String? = null

        // Create a storage reference from our app
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val mountainsRef = storageRef.child("item images").child(itemId)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        try {
            var uploadTask = mountainsRef.putBytes(data)
            uploadTask.await()

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                mountainsRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                     retVal = task.result.toString()
                } else {
                    // TODO:exception
                }
            }.await()
        } catch (e: Exception) {
            Log.w(TAG, "Error getting item documents: ", e)
        } finally {
        }

        return retVal
    }


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
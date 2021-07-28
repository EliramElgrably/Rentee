package com.example.rentee.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val modelFirebase: ModelFirebase
) {
    val user: LiveData<User?> = userDao.getUser().asLiveData()

    suspend fun getUserCheck(){
        if (Firebase.auth.currentUser == null) {
            deleteUser()
        } else {
            insertCurrentUser()
        }
    }

    suspend fun insertCurrentUser() {
        var user: User?
        FirebaseAuth.getInstance().currentUser?.let {
            user =
                User(
                    it.uid,
                    it.displayName,
                    it.email,
                    it.phoneNumber,
                    it.photoUrl.toString()
                )

            modelFirebase.uploadNewUserSuspendAwait(user!!).let {
                if (it) {
                    userDao.insert(user!!)
                }
            }
        }
    }

    suspend fun deleteUser() {
        userDao.deleteAllUsers()
    }

//    private suspend fun isLocalDBSyncWithRemote(): Boolean {
//        var retVal = false
//
//        this.user.collect {
//            if (it.userId == Firebase.auth.currentUser?.uid)
//                retVal = true
//        }
//        return retVal
//    }
}
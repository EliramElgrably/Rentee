package com.example.rentee.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val modelFirebase: ModelFirebase
) {
    val user: LiveData<User?> = userDao.getUser().asLiveData()

    suspend fun getUserCheck() {
        if (modelFirebase.isUserConnectedToFirebase() == null) {
            deleteUser()
        } else {
            insertCurrentUser()
        }
    }

    suspend fun insertCurrentUser() {
        var user: User?

        modelFirebase.isUserConnectedToFirebase()?.let {
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